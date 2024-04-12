package com.jerryalberto.mmas.feature.transaction.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.feature.transaction.model.TransactionGroup
import com.jerryalberto.mmas.feature.transaction.model.TransactionUIData
import com.jerryalberto.mmas.feature.transaction.ui.model.YearMonthItem
import com.jerryalberto.mmas.feature.transaction.ui.uistate.TransactionUIDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow<TransactionUIState>(TransactionUIState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _uiDataState = MutableStateFlow<TransactionUIData>(TransactionUIData())
    val uiDataState = _uiDataState.asStateFlow()

    init {
        getDataFromDB()
    }

    private fun getDataFromDB(){
        viewModelScope.launch {
            var latestYear = 0
            var latestMonth = 0
            transactionUseCase.getListOfYearMonth().asResult().collectLatest{ result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.value = TransactionUIState.Loading
                    }
                    is Result.Error -> {
                        _uiState.value = TransactionUIState.Error(exception = result.exception)
                    }
                    is Result.Success -> {
                        val listOfYearMonth =  result.data.mapIndexed  {index, yearMonth ->
                            val selected = (index == result.data.lastIndex)
                            if (selected) {
                                latestYear = yearMonth.year
                                latestMonth = yearMonth.month
                            }
                            YearMonthItem(
                                year = yearMonth.year,
                                month = yearMonth.month,
                                selected = selected
                            )
                        }
                        _uiDataState.value = uiDataState.value.copy(
                            listOfYearMonth = listOfYearMonth
                        )

                        if (latestYear > 0){
                            getTransactionsByYearMonth(year = latestYear, month = latestMonth)
                        }
                    }
                }
            }
        }
    }

    fun getTransactionsByYearMonth(year: Int, month: Int) {
        viewModelScope.launch {
            transactionUseCase.getAllTransactionByYearMonth(year = year, month = month).asResult()
                .collectLatest { result->
                    _uiState.value = when (result) {
                        is Result.Loading -> TransactionUIState.Loading
                        is Result.Error -> TransactionUIState.Error(exception = result.exception)
                        is Result.Success -> {
                            val transactionList = result.data.map { (date, transactions) ->
                                val totalAmount = transactions.sumOf { it.amount }
                                TransactionGroup(date, totalAmount, transactions)
                            }
                            _uiDataState.value = uiDataState.value.copy(
                                transactionList = transactionList,
                                listOfYearMonth = uiDataState.value.listOfYearMonth.map { item->
                                    if (item.year == year && item.month == month) {
                                        item.copy(selected = true) // Create a new item with selected set to true
                                    } else {
                                        item.copy(selected = false) // Create a new item with selected set to false (optional)
                                    }
                                }
                            )
                            TransactionUIState.Success
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
                        _uiState.value = TransactionUIState.Loading
                    }
                    is Result.Error -> {
                        _uiState.value = TransactionUIState.Error(exception = result.exception)
                    }
                    is Result.Success -> {
                        getDataFromDB()
                    }
                }
            }
        }
    }
}

sealed interface TransactionUIState {
    data object Initial : TransactionUIState
    data object Loading : TransactionUIState
    data object Success : TransactionUIState
    data class Error(val exception: Throwable) : TransactionUIState
}