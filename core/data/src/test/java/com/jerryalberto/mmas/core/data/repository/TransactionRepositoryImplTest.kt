package com.jerryalberto.mmas.core.data.repository

import com.jerryalberto.mmas.core.database.dao.TransactionDao
import com.jerryalberto.mmas.core.database.model.TransactionEntity
import com.jerryalberto.mmas.core.database.model.asExternalModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
                type = "",
                amount = 0.0,
                category = "",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            )
        )

        coEvery { dao.getTransactionByDate(date) } returns expectedMoney

        val actualMoney = moneyRepositoryImpl.getTransactionByDate(date)

        Assertions.assertEquals(expectedMoney.size, actualMoney.size)
        Assertions.assertTrue(actualMoney.isNotEmpty())

        Assertions.assertEquals(expectedMoney[0].asExternalModel(), actualMoney[0])

    }
}