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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.database.model.toTransaction
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.core.testing.data.ExceptionTestTubs
import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import kotlinx.coroutines.flow.flow

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

    @Test
    fun `test TransactionUseCase getLatestTransaction success case`() = runTest {
        coEvery { transactionRepository.getLatestTransaction(latest = 4) } returns flowOf(fourTransaction)


        transactionUseCase.getLatestTransaction().test {
            //Assertions.assertEquals(ExceptionTestTubs.exceptionStr, awaitError().message)

            val actualTransactions = awaitItem()

            Assertions.assertEquals(fourTransaction.size, actualTransactions.size)
            Assertions.assertTrue(actualTransactions.isNotEmpty())
            Assertions.assertEquals(fourTransaction[0], actualTransactions[0])

            awaitComplete()
        }

    }

    @Test
    fun `test TransactionUseCase getLatestTransaction fail case`() = runTest {
        coEvery { transactionRepository.getLatestTransaction(latest = 4) } returns flow {
            throw ExceptionTestTubs.NormalException
        }

        transactionUseCase.getLatestTransaction().test {
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr, awaitError().message)
        }
    }

    @Test
    fun `test TransactionUseCase getLatestTransaction asResult success case`() = runTest {
        coEvery { transactionRepository.getLatestTransaction(latest = 4) } returns flowOf(fourTransaction)

        transactionUseCase.getLatestTransaction().asResult().test {

            Assertions.assertEquals(Result.Loading, awaitItem())
            val successItem = awaitItem()

            Assertions.assertEquals(fourTransaction.size, (successItem as Result.Success<List<Transaction>>).data.size)
            Assertions.assertEquals(fourTransaction, (successItem as Result.Success<List<Transaction>>).data)

            awaitComplete()
        }

    }

    @Test
    fun `test TransactionUseCase getLatestTransaction asResult fail case`() = runTest {
        coEvery { transactionRepository.getLatestTransaction(latest = 4) } returns flow {
            throw ExceptionTestTubs.NormalException
        }

        /*
        //TODO: Fix later
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

        val actualResult = transactionUseCase.getLatestTransaction().asResult().toList()
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