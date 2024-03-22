package com.jerryalberto.mmas.core.domain.usecase

import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.domain.repository.TransactionRepository
import com.jerryalberto.mmas.core.model.data.Transaction
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.jerryalberto.mmas.core.common.result.Result

@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class TransactionUseCaseTest {

    @MockK
    private lateinit var transactionRepository: TransactionRepository

    private lateinit var transactionUseCase: TransactionUseCase

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

    @Test
    fun getLatestTransactionTest() = runTest {
        val transactions = listOf(
            Transaction(
                id = 1,
                type = "",
                amount = 0.0,
                category = "",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            ),
            Transaction(
                id = 2,
                type = "",
                amount = 1.0,
                category = "",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            )
        )

        coEvery { transactionRepository.getLatestTransaction() } returns flowOf(transactions)

        val actualTranactions = transactionUseCase.getLatestTransaction().first()

        Assertions.assertEquals(transactions.size, actualTranactions.size)
        Assertions.assertTrue(actualTranactions.isNotEmpty())

        Assertions.assertEquals(transactions[0], actualTranactions[0])

    }

    @Test
    fun getLatestTransactionWithLoading() = runTest {
        val transactions = listOf(
            Transaction(
                id = 1,
                type = "",
                amount = 0.0,
                category = "",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            ),
            Transaction(
                id = 2,
                type = "",
                amount = 1.0,
                category = "",
                description = "",
                date = Long.MAX_VALUE,
                hour = 1,
                minute = 1,
                uri = "",
            )
        )

        coEvery { transactionRepository.getLatestTransaction() } returns flowOf(transactions)

        val actualResult = transactionUseCase.getLatestTransaction().asResult().toList()

        Assertions.assertEquals(2, actualResult.size)

        Assertions.assertEquals(Result.Loading, actualResult[0])
        Assertions.assertEquals(transactions.size, (actualResult[1] as Result.Success<List<Transaction>>).data.size)
        Assertions.assertEquals(transactions, (actualResult[1] as Result.Success<List<Transaction>>).data)
    }
}