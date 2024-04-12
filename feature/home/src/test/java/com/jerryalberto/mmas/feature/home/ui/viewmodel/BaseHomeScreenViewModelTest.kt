package com.jerryalberto.mmas.feature.home.ui.viewmodel

import com.jerryalberto.mmas.core.database.model.toTransaction
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.model.data.TransactionSummary
import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.BeforeEach
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseHomeScreenViewModelTest {

    lateinit var viewModel: HomeScreenViewModel
    lateinit var transactionUseCase: TransactionUseCase

    val transactionList = TransactionsDataTestTubs.mockTodayTransactions.map {
        it.toTransaction()
    }

    val mockDeleteTransaction = transactionList[0].copy(
        id = 1
    )

    val transactionSummaryMap: Map<AccountBalanceDataType, TransactionSummary> = hashMapOf(
        AccountBalanceDataType.MONTH to TransactionsDataTestTubs.transactionSummary.copy(
            income = 80.0,
            expenses = 160.0
        ),
        AccountBalanceDataType.WEEK to TransactionsDataTestTubs.transactionSummary.copy(
            income = 40.0,
            expenses = 80.0
        ),
        AccountBalanceDataType.TODAY to TransactionsDataTestTubs.transactionSummary.copy(
            income = 10.0,
            expenses = 20.0
        ),
        AccountBalanceDataType.TOTAL to TransactionsDataTestTubs.transactionSummary,
    )

    @BeforeEach
    open fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        transactionUseCase = mockkClass(TransactionUseCase::class)
        mockkStatic(Timber::class)

        assignInitResult()

        viewModel = HomeScreenViewModel(
            transactionUseCase = transactionUseCase,
        )
    }

    open fun assignInitResult(){
        coEvery { transactionUseCase.getLatestTransaction() } returns flowOf(
            transactionList
        )
        transactionSummaryMap.forEach {
            coEvery { transactionUseCase.getSumAmountGroupedByType(it.key) } returns flowOf(
                it.value
            )
        }
    }


    @AfterEach
    fun after() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

}