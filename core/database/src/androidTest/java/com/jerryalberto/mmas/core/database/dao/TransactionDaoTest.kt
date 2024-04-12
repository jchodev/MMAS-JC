package com.jerryalberto.mmas.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jerryalberto.mmas.core.database.MmasDatabase
import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar


@RunWith(AndroidJUnit4::class)
class TransactionDaoTest {

    private lateinit var transactionDao: TransactionDao
    private lateinit var db: MmasDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MmasDatabase::class.java,
        ).build()
        transactionDao = db.transactionDao()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun getTransactionByIdTest() = runBlocking {
        transactionDao.deleteAllTransaction()

        //assign
        val transaction = TransactionsDataTestTubs.mockTodayTransactions[0]
        val id = transactionDao.insertTransaction(transaction)

        //action
        val result = transactionDao.getTransactionById(id).first()

        //verify
        assertEquals(transaction.type, result.type)

    }

    @Test
    fun getListOfYearMonthTest() = runBlocking {
        transactionDao.deleteAllTransaction()

        //assign
        TransactionsDataTestTubs.mockTodayTransactions.forEach {
            transactionDao.insertTransaction(it)
        }
        TransactionsDataTestTubs.lastMonthTransactions.forEach {
            transactionDao.insertTransaction(it)
        }

        //action
        val result = transactionDao.getListOfYearMonth().first()

        //verify
        assertEquals(2, result.size)
    }


    @Test
    fun getAllTransactionByYearMonthTest() = runBlocking {
        transactionDao.deleteAllTransaction()

        //assign
        TransactionsDataTestTubs.mockTodayTransactions.forEach {
            transactionDao.insertTransaction(it)
        }

        val year = TransactionsDataTestTubs.currentDateCalendar.get(Calendar.YEAR)
        val month = TransactionsDataTestTubs.currentDateCalendar.get(Calendar.MONTH) + 1 // Month is zero-based, so add 1
        //action
        val result = transactionDao.getAllTransactionByYearMonth(year = year, month = month).first()

        //verify
        assertEquals(TransactionsDataTestTubs.mockTodayTransactions.size, result.size)
    }

    @Test
    fun deleteTransactionByIdTest() = runBlocking {
        transactionDao.deleteAllTransaction()

        //assign
        val transaction = TransactionsDataTestTubs.mockTodayTransactions[0]
        val id = transactionDao.insertTransaction(transaction)
        val beforeDeleteResult = transactionDao.getAllTransaction().first()

        //verify
        assertEquals(1, beforeDeleteResult.size)

        //action
        transactionDao.deleteTransactionById(id)
        val afterDeleteResult = transactionDao.getAllTransaction().first()

        //verify
        assertEquals(true, afterDeleteResult.isEmpty())

    }

}