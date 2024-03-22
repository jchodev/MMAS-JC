package com.jerryalberto.mmas.feature.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.domain.usecase.CategoriesUseCase
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.feature.home.ui.helper.UiHelper
import com.jerryalberto.mmas.feature.home.ui.uistate.HomeUIDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.jerryalberto.mmas.core.common.result.Result
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val categoriesUseCase: CategoriesUseCase,
    private val transactionUseCase: TransactionUseCase,
    private val uiHelper: UiHelper,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUIDataState>(HomeUIDataState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            transactionUseCase.getLatestTransaction().combine(transactionUseCase.getSumAmountGroupedByType()) { transactions, summaries ->
                Pair(transactions, summaries)
            }.asResult().collect {
                when (it){
                    is Result.Loading -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = true
                            )
                        )
                    }
                    is Result.Success -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = false,
                                latestTransaction = it.data.first,
                                totalIncome = uiHelper.formatAmount(it.data.second.income),
                                totalExpenses = uiHelper.formatAmount(it.data.second.expenses),
                                totalAmount = uiHelper.formatAmount(it.data.second.income - it.data.second.expenses)
                            )
                        )
                    }
                    else -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = false,
                            )
                        )
                        //TODO
                    }
                }
            }
        }

    }

    fun getLastFourTransaction(){
        viewModelScope.launch {
            transactionUseCase.getLatestTransaction().asResult().collect {
                when (it){
                    is Result.Loading -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = true
                            )
                        )
                    }
                    is Result.Success -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = false,
                                latestTransaction = it.data
                            )
                        )
                    }
                    else -> {
                        //show some error
                    }
                }
            }
        }
    }

    private fun updateUI(uiState: HomeUIDataState){
        _uiState.value = uiState
    }

}