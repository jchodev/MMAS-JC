package com.jerryalberto.mmas.core.data.repository

import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import com.jerryalberto.mmas.core.database.dao.TransactionDao
import com.jerryalberto.mmas.core.database.model.TransactionYearMonthQueryResult
import com.jerryalberto.mmas.core.database.model.toTransaction
import com.jerryalberto.mmas.core.ext.convertMillisToYearMonthDay
import com.jerryalberto.mmas.core.ext.convertToDateMillis
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Calendar

@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class TransactionRepositoryImplTest {

    @MockK
    private lateinit var dao: TransactionDao

    private lateinit var moneyRepositoryImpl: TransactionRepositoryImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        moneyRepositoryImpl = TransactionRepositoryImpl(
            dao = dao,
            ioDispatcher = Dispatchers.IO
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test get transaction by date with expected result`() = runTest {
        val date = TransactionsDataTestTubs.currentDateCalendar
        val expectedTransaction = TransactionsDataTestTubs.mockTodayTransactions

        //assign
        val triple = date.timeInMillis.convertMillisToYearMonthDay()
        coEvery { dao.getTransactionByDate(
            year = triple.first,
            month = triple.second,
            day = triple.third
        ) } returns flowOf(expectedTransaction)

        //action
        val actualResult = moneyRepositoryImpl.getTransactionByDate(date.timeInMillis).first()

        //verify
        Assertions.assertEquals(expectedTransaction.size, actualResult.size)
        Assertions.assertTrue(actualResult.isNotEmpty())
        Assertions.assertEquals(expectedTransaction[0].toTransaction(), actualResult[0])
    }

    @Test
    fun `test get latest transaction expected return 4 record only`() = runTest {
        val expectedTransaction = TransactionsDataTestTubs.mockTodayTransactions

        //assign
        coEvery { dao.getLastTTransactionsByLimit(4) } returns flowOf(expectedTransaction.take(4))

        //action
        val actualResult = moneyRepositoryImpl.getLatestTransaction(4).first()

        //verify
        Assertions.assertEquals(4, actualResult.size)
    }

    @Test
    fun `test getAllTransactionGroupByDate return expected map`() = runTest {
        val expectedTransaction = TransactionsDataTestTubs.mockTodayTransactions +
                TransactionsDataTestTubs.lastWeekTransactions + TransactionsDataTestTubs.lastMonthTransactions

        //assign
        coEvery { dao.getAllTransaction() } returns flowOf(expectedTransaction)

        //action
        val actualResult = moneyRepositoryImpl.getAllTransactionGroupByDate().first()

        //verify
        Assertions.assertEquals(3, actualResult.size)

    }

    @Test
    fun `test getListOfYearMonth return expected result`() = runTest {
        //assign
        coEvery { dao.getListOfYearMonth() } returns flowOf( listOf(
            TransactionYearMonthQueryResult(
                year = 1,
                month = 1
            ),
            TransactionYearMonthQueryResult(
                year = 1,
                month = 2
            ),
        ))

        //action
        val actualResult = moneyRepositoryImpl.getListOfYearMonth().first()

        //verify
        Assertions.assertEquals(2, actualResult.size)
    }

    @Test
    fun `test getAllTransactionGroupByYearMonth return expected result`() = runTest {
        val year = TransactionsDataTestTubs.currentDateCalendar.get(Calendar.YEAR)
        val month = TransactionsDataTestTubs.currentDateCalendar.get(Calendar.MONTH) + 1 // Month is zero-based, so add 1

        //assign
        coEvery { dao.getAllTransactionByYearMonth(year = year, month = month) } returns flowOf(
            TransactionsDataTestTubs.mockTodayTransactions
        )

        //action
        val actualResult = moneyRepositoryImpl.getAllTransactionByYearMonth(year = year, month = month).first()

        //verify
        Assertions.assertEquals(1, actualResult.size)
        Assertions.assertEquals(TransactionsDataTestTubs.mockTodayTransactions.size, actualResult.values.first().size)
        val triple = Triple(
            first = TransactionsDataTestTubs.mockTodayTransactions.first().year,
            second = TransactionsDataTestTubs.mockTodayTransactions.first().month,
            third = TransactionsDataTestTubs.mockTodayTransactions.first().day
        )

        Assertions.assertEquals(triple.convertToDateMillis(), actualResult.keys.first())

    }


}