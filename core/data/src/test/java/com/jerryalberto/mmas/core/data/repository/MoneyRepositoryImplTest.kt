package com.jerryalberto.mmas.core.data.repository

import com.jerryalberto.mmas.core.database.dao.MoneyDao
import com.jerryalberto.mmas.core.database.model.MoneyEntity
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
class MoneyRepositoryImplTest {

    @MockK
    private lateinit var dao: MoneyDao

    private lateinit var moneyRepositoryImpl: MoneyRepositoryImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        moneyRepositoryImpl = MoneyRepositoryImpl(
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
        val date = "2024-02-03"
        val expectedMoney = listOf(
            MoneyEntity(
                id = 1,
                type = "",
                amount = 0.0,
                category = "",
                description = "",
                date = "2024-02-03",
                time = "time"
            )
        )

        coEvery { dao.getMoneyByDate(date) } returns expectedMoney

        val actualMoney = moneyRepositoryImpl.getMoneyByDate(date)

        Assertions.assertEquals(expectedMoney, actualMoney)
    }
}