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
                Assertions.assertEquals(HomeUIState.Initial, awaitItem())
                Assertions.assertEquals(HomeUIState.Loading, awaitItem())
                Assertions.assertEquals(HomeUIState.Success, awaitItem())
            }
        }
    }

    @TestFactory
    fun `test HomeScreenViewModel HomeUiData getAmountByType with different type collect expected success`() : List<DynamicTest> =
        transactionSummaryMap.map { map ->
            DynamicTest.dynamicTest(
                "test HomeScreenViewModel HomeUiData getAmountByType with ${map.key.value} collect expected success"
            ) {
                //action
                viewModel.getAmountByType(map.key)

                runTest {
                    viewModel.uiState.test {
                        //updated from init{}
                        awaitItem()
                        //verify
                        Assertions.assertEquals(HomeUIState.Loading, awaitItem())
                        Assertions.assertEquals(HomeUIState.Success, awaitItem())
                    }
                }
            }
        }

    @TestFactory
    fun `test HomeScreenViewModel HomeUiData getAmountByType with different type collect expected error`() : List<DynamicTest> =
        transactionSummaryMap.map { map ->
            DynamicTest.dynamicTest(
                "test HomeScreenViewModel HomeUiData getAmountByType with ${map.key.value} collect expected error"
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
                        //verify
                        Assertions.assertEquals(HomeUIState.Loading, awaitItem())
                        when (val errorResult = awaitItem()) {
                            is HomeUIState.Error -> {
                                Assertions.assertEquals(
                                    ExceptionTestTubs.exceptionStr,
                                    errorResult.exception.message,
                                )
                            }
                            else -> {}
                        }
                    }
                }
            }
        }

    @TestFactory
    fun `test HomeScreenViewModel HomeUIDataState getAmountByType with different type collect expected success`() : List<DynamicTest> =
        //because total amount will be got from init{} level
        transactionSummaryMap.filterNot { it.key == AccountBalanceDataType.TOTAL }.map { map ->
            DynamicTest.dynamicTest(
                "test HomeScreenViewModel HomeUIDataState getAmountByType with ${map.key.value} collect expected success"
            ) {
                //action
                viewModel.getAmountByType(map.key)

                runTest {
                    //action
                    viewModel.uiDataState.test {
                        //updated from init{}
                        awaitItem()

                        val actualHomeUiData = awaitItem()
                        //verify
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
    fun `test HomeScreenViewModel HomeUIDataState getAmountByType with different type collect expected error`() : List<DynamicTest> =
        //because total amount will be got from init{} level
        transactionSummaryMap.filterNot { it.key == AccountBalanceDataType.TOTAL }.map { map ->
            DynamicTest.dynamicTest(
                "test HomeScreenViewModel HomeUIDataState getAmountByType with ${map.key.value} collect expected error"
            ) {
                //assign
                coEvery { transactionUseCase.getSumAmountGroupedByType(any()) } returns flow{
                    throw ExceptionTestTubs.NormalException
                }

                //action
                viewModel.getAmountByType(map.key)

                runTest {

                    viewModel.uiDataState.test {
                        //updated from init{}
                        val currentItem = awaitItem()
                        //verify
                        Assertions.assertEquals(AccountBalanceDataType.TOTAL, currentItem.type)
                        Assertions.assertEquals(
                            transactionSummaryMap[AccountBalanceDataType.TOTAL]!!.income,
                            currentItem.totalIncome
                        )
                        Assertions.assertEquals(
                            transactionSummaryMap[AccountBalanceDataType.TOTAL]!!.expenses,
                            currentItem.totalExpenses
                        )
                        Assertions.assertEquals(transactionList, currentItem.latestTransaction)
                    }

                }
            }
        }

    @Test
    fun `test onTractionDelete HomeUIState with success`() = runTest {
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
            //verify
            Assertions.assertEquals(HomeUIState.Loading, awaitItem())
            Assertions.assertEquals(HomeUIState.Success, awaitItem())
        }
    }

    @Test
    fun `test onTractionDelete HomeUIState with Error`() = runTest {
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
            //verify
            Assertions.assertEquals(HomeUIState.Loading, awaitItem())
            when (val errorResult = awaitItem()) {
                is HomeUIState.Error -> {
                    Assertions.assertEquals(
                        ExceptionTestTubs.exceptionStr,
                        errorResult.exception.message,
                    )
                }
                else -> {}
            }
        }
    }

    @Test
    fun `test onTractionDelete uiDataState with success`() = runTest {
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

        viewModel.uiDataState.test {
            //updated from init{}
            awaitItem()
            //new update
            val actualUiState = awaitItem()
            //verify
            Assertions.assertEquals(AccountBalanceDataType.TOTAL, actualUiState.type)
            Assertions.assertEquals(
                afterDeleteTransactionSummary.income,
                actualUiState.totalIncome
            )
            Assertions.assertEquals(
                afterDeleteTransactionSummary.expenses,
                actualUiState.totalExpenses
            )
            Assertions.assertEquals(afterDeleteTransactionList, actualUiState.latestTransaction)
        }
    }

    @Test
    fun `test onTractionDelete uiDataState with error`() = runTest {
        //assign
        coEvery { transactionUseCase.deleteTransactionById(any()) } returns flow{
            throw ExceptionTestTubs.NormalException
        }

        //action
        viewModel.onTractionDelete(mockDeleteTransaction)
        viewModel.uiDataState.test {
            //updated from init{}
            //init{}
            val lastUiDataState = awaitItem()
            //verify
            Assertions.assertEquals(AccountBalanceDataType.TOTAL, lastUiDataState.type)
            Assertions.assertEquals(
                TransactionsDataTestTubs.transactionSummary.income,
                lastUiDataState.totalIncome
            )
            Assertions.assertEquals(
                TransactionsDataTestTubs.transactionSummary.expenses,
                lastUiDataState.totalExpenses
            )
            Assertions.assertEquals(transactionList, lastUiDataState.latestTransaction)
        }
    }
}