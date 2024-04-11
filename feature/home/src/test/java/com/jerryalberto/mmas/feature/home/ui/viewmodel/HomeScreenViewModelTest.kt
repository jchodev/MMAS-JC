package com.jerryalberto.mmas.feature.home.ui.viewmodel

import app.cash.turbine.test
import com.jerryalberto.mmas.core.database.model.toTransaction
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.model.data.TransactionSummary
import com.jerryalberto.mmas.core.testing.data.ExceptionTestTubs
import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import com.jerryalberto.mmas.feature.home.ui.data.HomeUiData
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {

    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var transactionUseCase: TransactionUseCase

    private val transactionList = TransactionsDataTestTubs.mockTodayTransactions.map {
        it.toTransaction()
    }

    private val mockDeleteTransaction = transactionList[0].copy(
        id = 1
    )

    private val transactionSummaryMap: Map<AccountBalanceDataType, TransactionSummary> = hashMapOf(
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
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        transactionUseCase = mockkClass(TransactionUseCase::class)
        mockkStatic(Timber::class)
    }

    private fun initSuccess(){
        coEvery { transactionUseCase.getLatestTransaction() } returns flowOf(
            transactionList
        )
        transactionSummaryMap.forEach{
            coEvery { transactionUseCase.getSumAmountGroupedByType(it.key) } returns flowOf(
                it.value
            )
        }

        viewModel = HomeScreenViewModel(
            transactionUseCase = transactionUseCase,
        )
    }

    private fun initFailed(){
        coEvery { transactionUseCase.getLatestTransaction() } throws ExceptionTestTubs.NormalException
        coEvery { transactionUseCase.getSumAmountGroupedByType(AccountBalanceDataType.TOTAL) } returns flowOf(
            TransactionsDataTestTubs.transactionSummary
        )
        viewModel = HomeScreenViewModel(
            transactionUseCase = transactionUseCase,
        )
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `test HomeScreenViewModel HomeUIState when init(getDataFromDB) collect expected success`() = runTest {
        initSuccess()
        viewModel.uiState.test {
            //verify
            Assertions.assertEquals(HomeUIState.Initial, awaitItem())
            Assertions.assertEquals(HomeUIState.Loading, awaitItem())
            Assertions.assertEquals(HomeUIState.Success, awaitItem())
        }
    }

    @Test
    fun `test HomeScreenViewModel HomeUIState when init(getDataFromDB) collect expected failed`() = runTest {
        initFailed()
        viewModel.uiState.test {
            //verify
            Assertions.assertEquals(HomeUIState.Initial, awaitItem())
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
    fun `test HomeScreenViewModel HomeUIDataState when init(getDataFromDB) collect expected success`() = runTest {
        initSuccess()
        viewModel.uiDataState.test {

            val originalHomeUiData = awaitItem()
            //verify
            Assertions.assertEquals(HomeUiData().type, originalHomeUiData.type)
            Assertions.assertEquals(HomeUiData().totalIncome, originalHomeUiData.totalIncome)
            Assertions.assertEquals(HomeUiData().totalExpenses, originalHomeUiData.totalExpenses)
            Assertions.assertEquals(
                HomeUiData().latestTransaction.size,
                originalHomeUiData.latestTransaction.size
            )

            val actualHomeUiData = awaitItem()
            //verify
            Assertions.assertEquals(AccountBalanceDataType.TOTAL, actualHomeUiData.type)
            Assertions.assertEquals(
                TransactionsDataTestTubs.transactionSummary.income,
                actualHomeUiData.totalIncome
            )
            Assertions.assertEquals(
                TransactionsDataTestTubs.transactionSummary.expenses,
                actualHomeUiData.totalExpenses
            )
            Assertions.assertEquals(transactionList, actualHomeUiData.latestTransaction)
        }
    }

    @TestFactory
    fun `test HomeScreenViewModel HomeUiData getAmountByType with different type collect expected success`() : List<DynamicTest> =
        transactionSummaryMap.map { map->
            DynamicTest.dynamicTest(
                "test HomeScreenViewModel HomeUiData getAmountByType with ${map.key.value} collect expected success"
            ){
                runTest {
                    initSuccess()
                    viewModel.uiState.test {

                        //init{}
                        Assertions.assertEquals(HomeUIState.Initial, awaitItem())
                        Assertions.assertEquals(HomeUIState.Loading, awaitItem())
                        Assertions.assertEquals(HomeUIState.Success, awaitItem())

                        //action
                        viewModel.getAmountByType(map.key)

                        //verify
                        Assertions.assertEquals(HomeUIState.Loading, awaitItem())
                        Assertions.assertEquals(HomeUIState.Success, awaitItem())
                    }
                }
            }
        }

    @TestFactory
    fun `test HomeScreenViewModel HomeUIDataState getAmountByType with different type collect expected success`() : List<DynamicTest> =
        //because total amount will be got from init{} level
        transactionSummaryMap.filterNot { it.key == AccountBalanceDataType.TOTAL }.map { map->
            DynamicTest.dynamicTest(
                "test HomeScreenViewModel HomeUIDataState getAmountByType with ${map.key.value} collect expected success"
            ){
                runTest {
                    initSuccess()
                    viewModel.uiDataState.test {
                        val originalHomeUiData = awaitItem()
                        //verify
                        Assertions.assertEquals(HomeUiData().type, originalHomeUiData.type)
                        Assertions.assertEquals(HomeUiData().totalIncome, originalHomeUiData.totalIncome)
                        Assertions.assertEquals(HomeUiData().totalExpenses, originalHomeUiData.totalExpenses)
                        Assertions.assertEquals(
                            HomeUiData().latestTransaction.size,
                            originalHomeUiData.latestTransaction.size
                        )

                        //init{}
                        val totalTypeHomeUiData = awaitItem()
                        //verify
                        Assertions.assertEquals(AccountBalanceDataType.TOTAL, totalTypeHomeUiData.type)
                        Assertions.assertEquals(
                            TransactionsDataTestTubs.transactionSummary.income,
                            totalTypeHomeUiData.totalIncome
                        )
                        Assertions.assertEquals(
                            TransactionsDataTestTubs.transactionSummary.expenses,
                            totalTypeHomeUiData.totalExpenses
                        )
                        Assertions.assertEquals(transactionList, totalTypeHomeUiData.latestTransaction)

                        //action
                        viewModel.getAmountByType(map.key)

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



    @Test
    fun `test onTractionDelete HomeUIState with success`() = runTest {
        initSuccess()
        coEvery { transactionUseCase.deleteTransactionById(mockDeleteTransaction.id) } returns flowOf(
            Unit
        )
        viewModel.uiState.test {

            //init{}
            Assertions.assertEquals(HomeUIState.Initial, awaitItem())
            Assertions.assertEquals(HomeUIState.Loading, awaitItem())
            Assertions.assertEquals(HomeUIState.Success, awaitItem())

            //action
            viewModel.onTractionDelete(mockDeleteTransaction)

            //verify
            Assertions.assertEquals(HomeUIState.Loading, awaitItem())
            Assertions.assertEquals(HomeUIState.Success, awaitItem())

            //awaitComplete()
        }
    }

    @Test
    fun `test onTractionDelete uiDataState with success`() = runTest {
        initSuccess()
        coEvery { transactionUseCase.deleteTransactionById(mockDeleteTransaction.id) } returns flowOf(
            Unit
        )
        viewModel.uiDataState.test {
            val originalHomeUiData = awaitItem()
            //verify
            Assertions.assertEquals(HomeUiData().type, originalHomeUiData.type)
            Assertions.assertEquals(HomeUiData().totalIncome, originalHomeUiData.totalIncome)
            Assertions.assertEquals(HomeUiData().totalExpenses, originalHomeUiData.totalExpenses)
            Assertions.assertEquals(
                HomeUiData().latestTransaction.size,
                originalHomeUiData.latestTransaction.size
            )

            //init{}
            val totalTypeHomeUiData = awaitItem()
            //verify
            Assertions.assertEquals(AccountBalanceDataType.TOTAL, totalTypeHomeUiData.type)
            Assertions.assertEquals(
                TransactionsDataTestTubs.transactionSummary.income,
                totalTypeHomeUiData.totalIncome
            )
            Assertions.assertEquals(
                TransactionsDataTestTubs.transactionSummary.expenses,
                totalTypeHomeUiData.totalExpenses
            )
            Assertions.assertEquals(transactionList, totalTypeHomeUiData.latestTransaction)
        }

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

        viewModel.uiDataState.test {
            //init{}
            awaitItem()

            //action
            viewModel.onTractionDelete(mockDeleteTransaction)

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
}


