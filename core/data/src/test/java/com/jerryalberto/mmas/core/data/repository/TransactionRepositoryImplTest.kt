package com.jerryalberto.mmas.core.data.repository

import com.jerryalberto.mmas.core.database.dao.TransactionDao
import com.jerryalberto.mmas.core.database.model.TransactionEntity
import com.jerryalberto.mmas.core.database.model.asExternalModel
import com.jerryalberto.mmas.core.model.data.Transaction
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
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
    fun getMoneyByDateTest() = runTest {
        val date = Long.MAX_VALUE
        val expectedMoney = listOf(
            TransactionEntity(
                id = 1,
                type = "INCOME",
                amount = 0.0,
                category = "FOOD",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            ),
            TransactionEntity(
                id = 2,
                type = "INCOME",
                amount = 1.0,
                category = "FOOD",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            )
        )

        coEvery { dao.getTransactionByDate(date) } returns flowOf(expectedMoney)

        val actualMoney = moneyRepositoryImpl.getTransactionByDate(date).first()

        Assertions.assertEquals(expectedMoney.size, actualMoney.size)
        Assertions.assertTrue(actualMoney.isNotEmpty())

        Assertions.assertEquals(expectedMoney[0].asExternalModel(), actualMoney[0])

    }

    @Test
    fun getLatestTransactionTest() = runTest {
        val expectedMoney = listOf(
            TransactionEntity(
                id = 1,
                type = "INCOME",
                amount = 0.0,
                category = "FOOD",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            ),
            TransactionEntity(
                id = 2,
                type = "INCOME",
                amount = 1.0,
                category = "FOOD",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            ),
            TransactionEntity(
                id = 3,
                type = "INCOME",
                amount = 1.0,
                category = "FOOD",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            ),
            TransactionEntity(
                id = 4,
                type = "INCOME",
                amount = 1.0,
                category = "FOOD",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            ),
            TransactionEntity(
                id = 5,
                type = "INCOME",
                amount = 1.0,
                category = "FOOD",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            )
        )

        coEvery { dao.getAllTransaction() } returns flowOf(expectedMoney)

        val actualMoney = moneyRepositoryImpl.getLatestTransaction(4).first()

        Assertions.assertEquals(4, actualMoney.size)
    }
}