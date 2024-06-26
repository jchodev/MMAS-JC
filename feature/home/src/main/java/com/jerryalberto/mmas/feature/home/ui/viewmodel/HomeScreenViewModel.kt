package com.jerryalberto.mmas.feature.home.ui.viewmodel

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.common.uistate.UIState
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.model.data.Transaction
 import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState(data = HomeUiData()))
    val uiState = _uiState.asStateFlow()

    init {
        getDataFromDB()
    }

    private fun getDataFromDB(){
        viewModelScope.launch {
            transactionUseCase.getLatestTransaction()
                    .combine(transactionUseCase.getSumAmountGroupedByType(AccountBalanceDataType.TOTAL))
            { transactions, summaries ->
                Pair(transactions, summaries)
            }.asResult().collectLatest { result->
                Timber.d("result:${result}")
                when (result) {
                    is Result.Loading -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = true
                            )
                        )
                    }
                    is Result.Error -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = false,
                                exception = result.exception,
                                data = HomeUiData(
                                    retryState = RetryState.GetDataFromDB
                                )
                            )
                        )
                    }
                    is Result.Success -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = false,
                                data = HomeUiData(
                                    type = AccountBalanceDataType.TOTAL,
                                    latestTransaction = result.data.first,
                                    totalIncome = result.data.second.income,
                                    totalExpenses = result.data.second.expenses,
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    fun getAmountByType(type: AccountBalanceDataType = AccountBalanceDataType.TOTAL) {
        viewModelScope.launch {
            transactionUseCase.getSumAmountGroupedByType(type).asResult().collectLatest{ result ->
                println("getAmountByType::result::${result}")
                when (result) {
                    is Result.Loading -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = true,
                            )
                        )
                    }
                    is Result.Error -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = false,
                                exception = result.exception,
                                data = uiState.value.data.copy(
                                    retryState = RetryState.GetAmountByType(type = type)
                                )
                            )
                        )
                    }
                    is Result.Success -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = false,
                                data = uiState.value.data.copy(
                                    type = type,
                                    totalIncome = result.data.income,
                                    totalExpenses = result.data.expenses,
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    fun onTractionDelete(transaction: Transaction){
        viewModelScope.launch {

            transactionUseCase.deleteTransactionById(id = transaction.id).asResult().collectLatest{ result ->
                when (result) {
                    is Result.Loading -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = true
                            )
                        )
                    }
                    is Result.Error -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = false,
                                exception = result.exception,
                                data = uiState.value.data.copy(
                                    retryState = RetryState.TractionDelete(transaction = transaction)
                                )
                            )
                        )
                    }
                    is Result.Success -> {
                        getDataFromDB()
                    }
                }
            }
        }
    }

    fun clearErrorMessage(){
        updateUI (
            uiState = uiState.value.copy(
                exception = null
            )
        )
    }

    fun retry(){
        updateUI (
            uiState = uiState.value.copy(
                exception = null
            )
        )
        when (val retry = uiState.value.data.retryState){
            is RetryState.GetDataFromDB -> getDataFromDB()
            is RetryState.TractionDelete -> onTractionDelete(transaction = retry.transaction)
            is RetryState.GetAmountByType -> getAmountByType(type = retry.type)
            else -> {

            }
        }
    }

    private fun updateUI(
        uiState: UIState<HomeUiData>
    ){
        _uiState.value = uiState
    }
}

@Parcelize
data class HomeUiData(
    val type: AccountBalanceDataType = AccountBalanceDataType.TOTAL,
    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val latestTransaction: List<Transaction> = listOf(),
    val retryState: @RawValue RetryState = RetryState.Initial
) : Parcelable {
    fun getTotalAmount () : Double {
        return totalIncome - totalExpenses
    }
}

sealed interface RetryState {
    data object Initial : RetryState
    data object GetDataFromDB : RetryState
    data class TractionDelete(val transaction: Transaction) : RetryState
    data class GetAmountByType(val type: AccountBalanceDataType) : RetryState
}