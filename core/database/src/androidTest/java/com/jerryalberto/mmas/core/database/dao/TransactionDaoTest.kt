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

        // Insert a sample transaction into the database
        val transaction = TransactionsDataTestTubs.todayTransactions[0]
        transactionDao.insertTransaction(transaction)
        val result = transactionDao.getTransactionById(transaction.id).first()
        assertEquals(transaction.id, result.id)
    }

    @Test
    fun getTransactionDatesTest() = runBlocking {
        transactionDao.deleteAllTransaction()

        //assign
        (TransactionsDataTestTubs.todayTransactions + TransactionsDataTestTubs.lastWeekTransactions + TransactionsDataTestTubs.lastMonthTransactions).forEach {
            db.transactionDao().insertTransaction(it)
        }

        //action
        val result = transactionDao.getTransactionDates().first()

        //verify
        assertEquals(3, result.size)
    }

}