package com.jerryalberto.mmas.feature.home.ui.viewmodel

import app.cash.turbine.test
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.testing.data.ExceptionTestTubs
import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import io.mockk.coEvery
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class HomeScreenViewModelNormalInitSuccessTest : BaseHomeScreenViewModelTest() {

    @BeforeEach
    override fun setUp() {
        super.setUp()

        runTest {
            viewModel.uiState.test {
                awaitItem()
                awaitItem()
                awaitItem()
            }
        }
    }

    @TestFactory
    fun `test HomeScreenViewModel UiData getAmountByType with different type collect expected success`() : List<DynamicTest> =
        transactionSummaryMap.map { map ->
            DynamicTest.dynamicTest(
                "test HomeScreenViewModel UiData getAmountByType with ${map.key.value} collect expected success"
            ) {
                //action
                viewModel.getAmountByType(map.key)

                runTest {
                    viewModel.uiState.test {
                        //updated from init{}
                        awaitItem()

                        //verify loading
                        Assertions.assertEquals(true, awaitItem().loading)

                        //verify success
                        val successItem = awaitItem()
                        Assertions.assertEquals(false,successItem.loading)
                        //verify success data level
                        val actualHomeUiData = successItem.data
                        Assertions.assertEquals(map.key, actualHomeUiData.type)
                        Assertions.assertEquals(
                            map.value.income,
                            actualHomeUiData.totalIncome
                        )
                        Assertions.assertEquals(
                            map.value.expenses,
                            actualHomeUiData.totalExpenses
                        )
                        Assertions.assertEquals(transactionList, actualHomeUiData.latestTransaction)
                    }
                }
            }
        }

    @TestFactory
    fun `test HomeScreenViewModel UiData getAmountByType with different type collect expected error`() : List<DynamicTest> =
        transactionSummaryMap.map { map ->
            DynamicTest.dynamicTest(
                "test HomeScreenViewModel UiData getAmountByType with ${map.key.value} collect expected error"
            ) {
                //assign
                coEvery { transactionUseCase.getSumAmountGroupedByType(any()) } returns flow{
                    throw ExceptionTestTubs.NormalException
                }

                //action
                viewModel.getAmountByType(map.key)

                runTest {
                    viewModel.uiState.test {
                        //updated from init{}
                        awaitItem()

                        //verify loading
                        Assertions.assertEquals(true, awaitItem().loading)

                        //verify error
                        val errorItem = awaitItem()
                        Assertions.assertEquals(false,errorItem.loading)
                        Assertions.assertEquals(ExceptionTestTubs.exceptionStr,errorItem.exception?.message)

                    }
                }
            }
        }


    @Test
    fun `test HomeScreenViewModel UiData onTractionDelete with success`() = runTest {
        //assign
        coEvery { transactionUseCase.deleteTransactionById(mockDeleteTransaction.id) } returns flowOf(
            Unit
        )
        val afterDeleteTransactionSummary = TransactionsDataTestTubs.transactionSummary.copy(
            income = 0.0
        )
        coEvery { transactionUseCase.getSumAmountGroupedByType(AccountBalanceDataType.TOTAL) } returns flowOf(
            afterDeleteTransactionSummary
        )
        val afterDeleteTransactionList = transactionList.subList(0, 1)
        coEvery { transactionUseCase.getLatestTransaction() } returns flowOf(
            afterDeleteTransactionList
        )

        //action
        viewModel.onTractionDelete(mockDeleteTransaction)

        //verify
        viewModel.uiState.test {
            //updated from init{}
            awaitItem()

            //verify loading
            Assertions.assertEquals(true, awaitItem().loading)

            //verify success
            val successItem = awaitItem()
            Assertions.assertEquals(false, successItem.loading)
            //verify success item data
            val actualUiState = successItem.data
            Assertions.assertEquals(AccountBalanceDataType.TOTAL, actualUiState.type)
            Assertions.assertEquals(
                afterDeleteTransactionSummary.income,
                actualUiState.totalIncome
            )
            Assertions.assertEquals(
                afterDeleteTransactionSummary.expenses,
                actualUiState.totalExpenses
            )
        }
    }

    @Test
    fun `test HomeScreenViewModel UiData onTractionDelete with error`() = runTest {
        //assign
        coEvery { transactionUseCase.deleteTransactionById(any()) } returns flow{
            throw ExceptionTestTubs.NormalException
        }

        //action
        viewModel.onTractionDelete(mockDeleteTransaction)
        viewModel.uiState.test {
            //updated from init{}
            awaitItem()


            //verify loading
            Assertions.assertEquals(true, awaitItem().loading)


            //verify error
            val errorItem = awaitItem()
            Assertions.assertEquals(false,errorItem.loading)
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr,errorItem.exception?.message)
        }
    }
}