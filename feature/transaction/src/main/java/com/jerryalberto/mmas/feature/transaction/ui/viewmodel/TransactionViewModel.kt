package com.jerryalberto.mmas.feature.transaction.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.database.model.toTransaction
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import com.jerryalberto.mmas.feature.transaction.model.TransactionData
import com.jerryalberto.mmas.feature.transaction.ui.model.YearMonthItem
import com.jerryalberto.mmas.feature.transaction.ui.uistate.TransactionUIDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel  @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
): ViewModel(){

    private val _uiState = MutableStateFlow<TransactionUIDataState>(TransactionUIDataState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            showLoading(true)
            showLoading(true)
            var latestYear = 0
            var latestMonth = 0

            transactionUseCase.getListOfYearMonth().asResult().collect{
                when (it){
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        val listOfYearMonth = it.data.mapIndexed  {index, yearMonth ->
                            val selected = (index == it.data.lastIndex)
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

                        updateUI (
                            uiState = uiState.value.copy(
                                listOfYearMonth = listOfYearMonth
                            )
                        )
                        // get transaction by year and month
                        if (latestYear > 0){
                            getTransactionsByYearMonth(year = latestYear, month = latestMonth)
                        }

                    }
                    else -> {
                        //TODO
                        showLoading(false)
                    }
                }
            }


        }
    }

    fun getTransactionsByYearMonth(year: Int, month: Int) {
        viewModelScope.launch {
            transactionUseCase.getAllTransactionByYearMonth(year = year, month = month).asResult()
                .collect {
                    when (it) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            val transactionList = it.data.map { (date, transactions) ->
                                val totalAmount = transactions.sumOf { it.amount }
                                TransactionData(date, totalAmount, transactions)
                            }
                            updateUI (
                                uiState = uiState.value.copy(
                                    transactionList = transactionList,
                                    listOfYearMonth = uiState.value.listOfYearMonth.map { item->
                                        if (item.year == year && item.month == month) {
                                            item.copy(selected = true) // Create a new item with selected set to true
                                        } else {
                                            item.copy(selected = false) // Create a new item with selected set to false (optional)
                                        }
                                    }
                                )
                            )
                            showLoading(false)
                        }
                        else -> {
                            //TODO
                            showLoading(false)
                        }
                    }
                }
        }
    }

    private fun showLoading(show: Boolean){
        updateUI (
            uiState = uiState.value.copy(
                loading = show
            )
        )
    }

    private fun updateUI(uiState: TransactionUIDataState){
        _uiState.value = uiState
    }

    private suspend fun insertTestDate(){
        TransactionsDataTestTubs.todayTransactions.forEach {
            transactionUseCase.insertTransaction(it.toTransaction())
        }

        TransactionsDataTestTubs.lastWeekTransactions.forEach {
            transactionUseCase.insertTransaction(it.toTransaction())
        }

        TransactionsDataTestTubs.lastMonthTransactions.forEach {
            transactionUseCase.insertTransaction(it.toTransaction())
        }
    }
}