package com.jerryalberto.mmas.core.domain.usecase

import app.cash.turbine.test
import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.domain.repository.TransactionRepository
import com.jerryalberto.mmas.core.model.data.Transaction
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.database.model.TransactionYearMonthQueryResult
import com.jerryalberto.mmas.core.database.model.toTransaction
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.model.data.TransactionSummary
import com.jerryalberto.mmas.core.testing.data.ExceptionTestTubs
import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import io.mockk.coJustRun
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.Calendar

@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class TransactionUseCaseTest {

    @MockK
    private lateinit var transactionRepository: TransactionRepository

    private lateinit var transactionUseCase: TransactionUseCase

    private val transactionList = TransactionsDataTestTubs.mockTodayTransactions.map {
        it.toTransaction()
    }

    private val fourTransaction = transactionList.take(4)

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        transactionUseCase = TransactionUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    //getLatestTransaction
    @Test
    fun `test TransactionUseCase getLatestTransaction success case`() = runTest {
        coEvery { transactionRepository.getLatestTransaction(latest = 4) } returns flowOf(fourTransaction)

        //action and verify
        transactionUseCase.getLatestTransaction().test {
            val actualTransactions = awaitItem()
            Assertions.assertEquals(fourTransaction.size, actualTransactions.size)
            Assertions.assertTrue(actualTransactions.isNotEmpty())
            Assertions.assertEquals(fourTransaction[0], actualTransactions[0])
            awaitComplete()
        }

        //action and verify (asResult)
        transactionUseCase.getLatestTransaction().asResult().test {

            Assertions.assertEquals(Result.Loading, awaitItem())
            val successItem = awaitItem()

            Assertions.assertEquals(fourTransaction.size, (successItem as Result.Success<List<Transaction>>).data.size)
            Assertions.assertEquals(fourTransaction, (successItem as Result.Success<List<Transaction>>).data)

            awaitComplete()
        }
    }

    @Test
    fun `test TransactionUseCase getLatestTransaction fail case`() = runTest {
        coEvery { transactionRepository.getLatestTransaction(latest = 4) } returns flow {
            throw ExceptionTestTubs.NormalException
        }

        //action and verify
        transactionUseCase.getLatestTransaction().test {
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr, awaitError().message)
        }

        //action and verify(asResult)
        val actualResult = transactionUseCase.getLatestTransaction().asResult().toList()
        Assertions.assertEquals(Result.Loading, actualResult[0])
        when (val errorResult = actualResult[1]) {
            is Result.Error -> {
                Assertions.assertEquals(ExceptionTestTubs.exceptionStr, errorResult.exception.message)
            }
            else -> {

            }
        }

        /* TODO unknow not work
        transactionUseCase.getLatestTransaction().asResult().test {

            Assertions.assertEquals(Result.Loading, awaitItem())
            when (val errorResult = awaitItem()) {
                is Result.Error -> {
                    Assertions.assertEquals(ExceptionTestTubs.exceptionStr, errorResult.exception.message)
                }
                else -> {

                }
            }
        }
         */
    }


    //insertTransaction
    @Test
    fun `test TransactionUseCase insertTransaction success case`() = runTest {
        coEvery { transactionRepository.insertTransaction(any()) } returns 1

        //action and verify
        transactionUseCase.insertTransaction(transactionList[0]).test {
            Assertions.assertEquals(1, awaitItem())
            awaitComplete()
        }

        //action and verify(asResult)
        transactionUseCase.insertTransaction(transactionList[0]).asResult().test {
            Assertions.assertEquals(Result.Loading, awaitItem())
            Assertions.assertEquals(1, (awaitItem() as Result.Success<Long>).data)
            awaitComplete()
        }
    }

    @Test
    fun `test TransactionUseCase insertTransaction fail case`() = runTest {
        coEvery { transactionRepository.insertTransaction(any()) } throws ExceptionTestTubs.NormalException

        //action and verify
        transactionUseCase.insertTransaction(transactionList[0]).test {
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr, awaitError().message)
        }

        //action and verify(asResult)
        val actualResult = transactionUseCase.insertTransaction(transactionList[0]).asResult().toList()
        Assertions.assertEquals(Result.Loading, actualResult[0])
        when (val errorResult = actualResult[1]) {
            is Result.Error -> {
                Assertions.assertEquals(ExceptionTestTubs.exceptionStr, errorResult.exception.message)
            }
            else -> {

            }
        }
    }

    //deleteTransactionById
    @Test
    fun `test TransactionUseCase deleteTransactionById success case`() = runTest {
        coJustRun { transactionRepository.deleteTransactionById(any()) }

        //action and verify
        val result = transactionUseCase.deleteTransactionById(1).first()
        Assertions.assertTrue(result == Unit)

        //action and verify(asResult)
        transactionUseCase.deleteTransactionById(1).asResult().test {
            Assertions.assertEquals(Result.Loading, awaitItem())
            Assertions.assertTrue((awaitItem() as Result.Success<Unit>).data == Unit)
            awaitComplete()
        }
    }

    @Test
    fun `test TransactionUseCase deleteTransactionById fail case`() = runTest {
        coEvery { transactionRepository.deleteTransactionById(any()) } throws ExceptionTestTubs.NormalException

        //action and verify
        transactionUseCase.deleteTransactionById(1).test {
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr, awaitError().message)
        }

        //action and verify(asResult)
        val actualResult = transactionUseCase.deleteTransactionById(1).asResult().toList()
        Assertions.assertEquals(Result.Loading, actualResult[0])
        when (val errorResult = actualResult[1]) {
            is Result.Error -> {
                Assertions.assertEquals(ExceptionTestTubs.exceptionStr, errorResult.exception.message)
            }
            else -> {

            }
        }
    }

    //deleteAllTransaction
    @Test
    fun `test TransactionUseCase deleteAllTransaction success case`() = runTest {
        coJustRun { transactionRepository.deleteAllTransaction() }

        //action and verify
        val result = transactionUseCase.deleteAllTransaction().first()
        Assertions.assertTrue(result == Unit)

        //action and verify(asResult)
        transactionUseCase.deleteAllTransaction().asResult().test {
            Assertions.assertEquals(Result.Loading, awaitItem())
            Assertions.assertTrue((awaitItem() as Result.Success<Unit>).data == Unit)
            awaitComplete()
        }
    }

    @Test
    fun `test TransactionUseCase deleteAllTransaction fail case`() = runTest {
        coEvery { transactionRepository.deleteAllTransaction() } throws ExceptionTestTubs.NormalException

        //action and verify
        transactionUseCase.deleteAllTransaction().test {
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr, awaitError().message)
        }

        //action and verify(asResult)
        val actualResult = transactionUseCase.deleteAllTransaction().asResult().toList()
        Assertions.assertEquals(Result.Loading, actualResult[0])
        when (val errorResult = actualResult[1]) {
            is Result.Error -> {
                Assertions.assertEquals(ExceptionTestTubs.exceptionStr, errorResult.exception.message)
            }
            else -> {

            }
        }
    }

    private val accountBalanceDataTypeList  = enumValues<AccountBalanceDataType>().toList()
    @TestFactory
    fun `test TransactionUseCase getSumAmountGroupedByType with different type collect expected success`() : List<DynamicTest> =
        accountBalanceDataTypeList.map {accountBalanceDataType ->
            DynamicTest.dynamicTest("test TransactionUseCase getSumAmountGroupedByType with ${accountBalanceDataType.value} collect expected success") {
                val transactionSummaryQueryList = listOf(
                    TransactionsDataTestTubs.transactionSummaryQueryIncome,
                    TransactionsDataTestTubs.transactionSummaryQueryExpenses
                )
                runTest {
                    coEvery { transactionRepository.getAllGroupedAmount() } returns flowOf(
                        transactionSummaryQueryList
                    )
                    coEvery { transactionRepository.getGroupedAmountByDateRange(any(), any()) }  returns flowOf(
                        transactionSummaryQueryList
                    )

                    //action and verify
                    val result = transactionUseCase.getSumAmountGroupedByType(accountBalanceDataType).first()
                    Assertions.assertEquals( TransactionsDataTestTubs.transactionIncomeAmount, result.income )
                    Assertions.assertEquals( TransactionsDataTestTubs.transactionExpenseAmount, result.expenses )

                    //action and verify(asResult)
                    transactionUseCase.getSumAmountGroupedByType(accountBalanceDataType).asResult().test {
                        Assertions.assertEquals(Result.Loading, awaitItem())
                        val result = ((awaitItem() as Result.Success<TransactionSummary>)).data
                        Assertions.assertEquals( TransactionsDataTestTubs.transactionIncomeAmount, result.income )
                        Assertions.assertEquals( TransactionsDataTestTubs.transactionExpenseAmount, result.expenses )
                        awaitComplete()
                    }
                }
            }
        }

    @TestFactory
    fun `test TransactionUseCase getSumAmountGroupedByType with different type collect expected error`() : List<DynamicTest> =
        accountBalanceDataTypeList.map {accountBalanceDataType ->
            DynamicTest.dynamicTest("test TransactionUseCase getSumAmountGroupedByType with ${accountBalanceDataType.value} collect expected error") {
                runTest {
                    coEvery { transactionRepository.getAllGroupedAmount() } returns flow {
                        throw ExceptionTestTubs.NormalException
                    }
                    coEvery { transactionRepository.getGroupedAmountByDateRange(any(), any()) }  returns flow {
                        throw ExceptionTestTubs.NormalException
                    }

                    //action and verify
                    transactionUseCase.getSumAmountGroupedByType(accountBalanceDataType).test {
                        Assertions.assertEquals(ExceptionTestTubs.exceptionStr, awaitError().message)
                    }

                    //action and verify(asResult)
                    val actualResult = transactionUseCase.getSumAmountGroupedByType(accountBalanceDataType).asResult().toList()
                    Assertions.assertEquals(Result.Loading, actualResult[0])
                    when (val errorResult = actualResult[1]) {
                        is Result.Error -> {
                            Assertions.assertEquals(ExceptionTestTubs.exceptionStr, errorResult.exception.message)
                        }
                        else -> {

                        }
                    }
                }
            }
        }

    //getListOfYearMonth
    @Test
    fun `test TransactionUseCase getListOfYearMonth success case`() = runTest {
        coEvery { transactionRepository.getListOfYearMonth() } returns flowOf(TransactionsDataTestTubs.transactionYearMonthQueryResultList)

        //action and verify
        transactionUseCase.getListOfYearMonth().test {
            Assertions.assertEquals(TransactionsDataTestTubs.transactionYearMonthQueryResultList, awaitItem())
            awaitComplete()
        }

        //action and verify (asResult)
        transactionUseCase.getListOfYearMonth().asResult().test {

            Assertions.assertEquals(Result.Loading, awaitItem())
            val successItem = awaitItem()
            Assertions.assertEquals(TransactionsDataTestTubs.transactionYearMonthQueryResultList, (successItem as Result.Success<List<TransactionYearMonthQueryResult>>).data)
            awaitComplete()
        }
    }

    @Test
    fun `test TransactionUseCase getListOfYearMonth fail case`() = runTest {
        coEvery { transactionRepository.getListOfYearMonth() } returns flow {
            throw ExceptionTestTubs.NormalException
        }

        //action and verify
        transactionUseCase.getListOfYearMonth().test {
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr, awaitError().message)
        }

        //action and verify (asResult)
        val actualResult = transactionUseCase.getListOfYearMonth().asResult().toList()
        Assertions.assertEquals(Result.Loading, actualResult[0])
        when (val errorResult = actualResult[1]) {
            is Result.Error -> {
                Assertions.assertEquals(ExceptionTestTubs.exceptionStr, errorResult.exception.message)
            }
            else -> {

            }
        }
    }

    //getAllTransactionByYearMonth
    @Test
    fun `test TransactionUseCase getAllTransactionByYearMonth success case`() = runTest {
        val expectedResult = mapOf(
            1L to TransactionsDataTestTubs.mockTodayTransactions.map{ it.toTransaction()}
        )
        coEvery { transactionRepository.getAllTransactionByYearMonth(any(), any() ) } returns flowOf(
            expectedResult
        )

        //action and verify
        transactionUseCase.getAllTransactionByYearMonth(1, 2).test {
            Assertions.assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }

        //action and verify (asResult)
        transactionUseCase.getAllTransactionByYearMonth(1, 2).asResult().test {

            Assertions.assertEquals(Result.Loading, awaitItem())
            val successItem = awaitItem()
            Assertions.assertEquals(expectedResult, (successItem as Result.Success<Map<Long, List<Transaction>>>).data)
            awaitComplete()
        }
    }

    @Test
    fun `test TransactionUseCase getAllTransactionByYearMonth fail case`() = runTest {
        coEvery { transactionRepository.getAllTransactionByYearMonth(any(), any() ) }  returns flow {
            throw ExceptionTestTubs.NormalException
        }

        //action and verify
        transactionUseCase.getAllTransactionByYearMonth(1, 2).test {
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr, awaitError().message)
        }

        //action and verify(asResult)
        val actualResult = transactionUseCase.getAllTransactionByYearMonth(1, 2).asResult().toList()
        Assertions.assertEquals(Result.Loading, actualResult[0])
        when (val errorResult = actualResult[1]) {
            is Result.Error -> {
                Assertions.assertEquals(ExceptionTestTubs.exceptionStr, errorResult.exception.message)
            }
            else -> {

            }
        }
    }
}