package com.jerryalberto.mmas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.database.model.toTransaction
import com.jerryalberto.mmas.core.domain.usecase.SettingUseCase
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val settingUseCase: SettingUseCase,
) : ViewModel() {

    private val _readyStatue = MutableStateFlow<Boolean?>(null)
    val readyStatue = _readyStatue.asStateFlow()

    fun init() {
        viewModelScope.launch {
            settingUseCase.getSetting().asResult().collectLatest {
                when (it) {
                    is Result.Loading -> {
                       // showLoading(true)
                    }
                    is Result.Success -> {
                        transactionUseCase.deleteAllTransaction()
                        TransactionsDataTestTubs.mockTodayTransactions.forEach {
                            transactionUseCase.insertTransaction(it.toTransaction())
                        }

                        TransactionsDataTestTubs.lastWeekTransactions.forEach {
                            transactionUseCase.insertTransaction(it.toTransaction())
                        }

                        TransactionsDataTestTubs.lastMonthTransactions.forEach {
                            transactionUseCase.insertTransaction(it.toTransaction())
                        }

                        _readyStatue.value = true
                    }
                    else -> {
                        //TODO
                        //showLoading(false)
                    }
                }
            }
            //_readyStatue.value = true
        }
    }

}