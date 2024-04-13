package com.jerryalberto.mmas.feature.transaction.ui.viewmodel

import app.cash.turbine.test

import com.jerryalberto.mmas.core.testing.data.ExceptionTestTubs

import io.mockk.coEvery
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransactionScreenViewModelNormalInitSuccessTest: BaseTransactionViewModelTest() {


    val mockDeleteTransaction = todayTransaction[0].copy(
        id = 1
    )

    @BeforeEach
    override fun setUp() {
        super.setUp()

        runTest {
            viewModel.uiState.test {
                awaitItem()
                awaitItem()
                awaitItem()
                awaitItem()
            }
        }
    }

    @Test
    fun `test TransactionScreenViewModel UiData getTransactionsByYearMonth with success`() = runTest {
        //action
        viewModel.getTransactionsByYearMonth(1, 1)

        //verify
        viewModel.uiState.test {

            //updated from init{}
            awaitItem()

            //verify loading
            val loadingItem = awaitItem()
            Assertions.assertEquals(true, loadingItem.loading)
            Assertions.assertEquals(null, loadingItem.exception)

            //verify success
            val successItem = awaitItem()
            Assertions.assertEquals(false, successItem.loading)
            Assertions.assertEquals(null, successItem.exception)
            val successItemData = successItem.data
            successItemData.transactionList.forEach {
                Assertions.assertEquals(allTransactionResult.get(it.date), it.transactions)
            }
        }
    }

    @Test
    fun `test TransactionScreenViewModel UiData getTransactionsByYearMonth with error`() = runTest {
        //assign
        coEvery { transactionUseCase.getAllTransactionByYearMonth(any(), any()) } returns flow{
            throw ExceptionTestTubs.NormalException
        }
        //action
        viewModel.getTransactionsByYearMonth(1, 1)

        //verify
        viewModel.uiState.test {

            //updated from init{}
            awaitItem()

            //verify loading
            val loadingItem = awaitItem()
            Assertions.assertEquals(true, loadingItem.loading)
            Assertions.assertEquals(null, loadingItem.exception)

            //verify error
            val errorItem = awaitItem()
            Assertions.assertEquals(false,errorItem.loading)
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr,errorItem.exception?.message)
        }
    }

    @Test
    fun `test TransactionScreenViewModel UiData onTractionDelete with success`() = runTest {
        //assign
        coEvery { transactionUseCase.deleteTransactionById(mockDeleteTransaction.id) } returns flowOf(
            Unit
        )

        //action
        viewModel.onTractionDelete(mockDeleteTransaction)

        //verify
        viewModel.uiState.test {


            //updated from init{}
            awaitItem()

            //verify loading
            val loadingItem = awaitItem()
            Assertions.assertEquals(true, loadingItem.loading)
            Assertions.assertEquals(null, loadingItem.exception)

            //verify getDataFromDB - success
            val successItem = awaitItem()
            Assertions.assertEquals(null, successItem.exception)

        }
    }

    @Test
    fun `test TransactionScreenViewModel UiData onTractionDelete with Error`() = runTest {
        //assign
        coEvery { transactionUseCase.deleteTransactionById(mockDeleteTransaction.id) } returns flow{
            throw ExceptionTestTubs.NormalException
        }

        //action
        viewModel.onTractionDelete(mockDeleteTransaction)

        //verify
        viewModel.uiState.test {


            //updated from init{}
            awaitItem()

            //verify loading
            val loadingItem = awaitItem()
            Assertions.assertEquals(true, loadingItem.loading)
            Assertions.assertEquals(null, loadingItem.exception)

            //verify error
            val errorItem = awaitItem()
            Assertions.assertEquals(false,errorItem.loading)
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr,errorItem.exception?.message)

        }

    }

}