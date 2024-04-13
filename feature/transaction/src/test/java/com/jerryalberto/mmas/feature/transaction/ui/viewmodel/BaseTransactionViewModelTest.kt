package com.jerryalberto.mmas.feature.transaction.ui.viewmodel

import com.jerryalberto.mmas.core.database.model.toTransaction
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import com.jerryalberto.mmas.feature.transaction.ui.model.YearMonthItem
import io.mockk.coEvery
import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseTransactionViewModelTest {

    lateinit var viewModel: TransactionViewModel
    lateinit var transactionUseCase: TransactionUseCase


    val todayTransaction = TransactionsDataTestTubs.mockTodayTransactions.map{ it.toTransaction()}
    val lastMonthTransaction = TransactionsDataTestTubs.lastMonthTransactions.map{ it.toTransaction()}

    val allTransactionResult = mapOf(
        1L to lastMonthTransaction,
        2L to todayTransaction
    )

    val exceptedYearMonthItemList = listOf(
        YearMonthItem(
            year = 1,
            month = 1,
            selected = false,
        ),
        YearMonthItem(
            year = 1,
            month = 2,
            selected = true,
        )
    )

    @BeforeEach
    open fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        transactionUseCase = mockkClass(TransactionUseCase::class)
        mockkStatic(Timber::class)

        assignInitResult()

        viewModel = TransactionViewModel(
            transactionUseCase = transactionUseCase,
        )
    }

    open fun assignInitResult(){
        coEvery { transactionUseCase.getListOfYearMonth() } returns flowOf(
            TransactionsDataTestTubs.transactionYearMonthQueryResultList
        )
        coEvery { transactionUseCase.getAllTransactionByYearMonth(any(), any()) } returns flowOf(
            allTransactionResult
        )
    }
}