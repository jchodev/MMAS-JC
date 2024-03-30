package com.jerryalberto.mmas.core.data.repository

import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import com.jerryalberto.mmas.core.database.dao.TransactionDao
import com.jerryalberto.mmas.core.database.model.asExternalModel
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
        val expectedTransaction = TransactionsDataTestTubs.todayTransactions

        //assign
        coEvery { dao.getTransactionByDate(date.timeInMillis) } returns flowOf(expectedTransaction)

        //action
        val actualResult = moneyRepositoryImpl.getTransactionByDate(date.timeInMillis).first()

        //verify
        Assertions.assertEquals(expectedTransaction.size, actualResult.size)
        Assertions.assertTrue(actualResult.isNotEmpty())
        Assertions.assertEquals(expectedTransaction[0].asExternalModel(), actualResult[0])
    }

    @Test
    fun `test get latest transaction expected return 4 record only`() = runTest {
        val expectedTransaction = TransactionsDataTestTubs.todayTransactions

        //assign
        coEvery { dao.getLastTTransactionsByLimit(4) } returns flowOf(expectedTransaction.take(4))

        //action
        val actualResult = moneyRepositoryImpl.getLatestTransaction(4).first()

        //verify
        Assertions.assertEquals(4, actualResult.size)
    }

    @Test
    fun `test getAllTransactionGroupByDate return expected map`() = runTest {
        val expectedTransaction = TransactionsDataTestTubs.todayTransactions +
                TransactionsDataTestTubs.lastWeekTransactions + TransactionsDataTestTubs.lastMonthTransactions

        //assign
        coEvery { dao.getAllTransaction() } returns flowOf(expectedTransaction)

        //action
        val actualResult = moneyRepositoryImpl.getAllTransactionGroupByDate().first()

        //verify
        Assertions.assertEquals(3, actualResult.size)

    }

    //getTransactionDates
    @Test
    fun `test getTransactionDates return expected long`() = runTest {
        //assign
        coEvery { dao.getTransactionDates() } returns flowOf(listOf(
            TransactionsDataTestTubs.currentDateCalendar.timeInMillis,
            TransactionsDataTestTubs.getLastWeekDateDateMillis().timeInMillis,
            TransactionsDataTestTubs.getLastMonthDateDateMillis().timeInMillis,
        ))

        //action
        val actualResult = moneyRepositoryImpl.getTransactionDates().first()

        //verify
        Assertions.assertEquals(3, actualResult.size)
    }
}