package com.jerryalberto.mmas.feature.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.feature.home.ui.data.HomeUiData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _uiDataState = MutableStateFlow<HomeUiData>(HomeUiData())
    val uiDataState = _uiDataState.asStateFlow()

    init {
        getDataFromDB()
    }

    private fun getDataFromDB(){
        viewModelScope.launch {
            transactionUseCase.getLatestTransaction().combine(transactionUseCase.getSumAmountGroupedByType(AccountBalanceDataType.TOTAL)) { transactions, summaries ->
                Pair(transactions, summaries)
            }.asResult().collectLatest { result->
                Timber.d("result:${result}")
                _uiState.value = when (result) {
                    is Result.Loading -> HomeUIState.Loading
                    is Result.Error -> HomeUIState.Error(exception = result.exception)
                    is Result.Success -> {
                        _uiDataState.value = HomeUiData(
                            type = AccountBalanceDataType.TOTAL,
                            latestTransaction = result.data.first,
                            totalIncome = result.data.second.income,
                            totalExpenses = result.data.second.expenses,
                        )
                        HomeUIState.Success
                    }
                }
            }
        }
    }

    fun getAmountByType(type: AccountBalanceDataType = AccountBalanceDataType.TOTAL) {
        viewModelScope.launch {
            transactionUseCase.getSumAmountGroupedByType(type).asResult().collectLatest{ result ->
                println("getAmountByType::result::${result}")
                _uiState.value = when (result) {
                    is Result.Loading -> HomeUIState.Loading
                    is Result.Error -> HomeUIState.Error(exception = result.exception)
                    is Result.Success -> {
                        _uiDataState.value = uiDataState.value.copy(
                            type = type,
                            totalIncome = result.data.income,
                            totalExpenses = result.data.expenses,
                        )
                        HomeUIState.Success
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
                        _uiState.value = HomeUIState.Loading
                    }
                    is Result.Error -> {
                        _uiState.value = HomeUIState.Error(exception = result.exception)
                    }
                    is Result.Success -> {
                        getDataFromDB()
                    }
                }
            }
        }
    }

}

sealed interface HomeUIState {
    data object Initial : HomeUIState
    data object Loading : HomeUIState
    data object Success : HomeUIState
    data class Error(val exception: Throwable) : HomeUIState
}