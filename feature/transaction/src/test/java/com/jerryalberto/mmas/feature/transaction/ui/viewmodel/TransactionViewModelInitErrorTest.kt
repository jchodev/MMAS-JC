package com.jerryalberto.mmas.feature.transaction.ui.viewmodel

import app.cash.turbine.test
import com.jerryalberto.mmas.core.testing.data.ExceptionTestTubs
import com.jerryalberto.mmas.feature.transaction.model.TransactionUIData
import io.mockk.coEvery
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TransactionViewModelInitErrorTest: BaseTransactionViewModelTest() {

    override fun assignInitResult() {
        coEvery { transactionUseCase.getListOfYearMonth() } returns flow {
            throw ExceptionTestTubs.NormalException
        }
    }

    @Test
    fun `test TransactionViewModel UIState when init(getDataFromDB) collect expected error`() = runTest {
        viewModel.uiState.test {
            //verify origin
            val originItem = awaitItem()
            Assertions.assertEquals(false, originItem.loading)
            Assertions.assertEquals(null, originItem.exception)
            Assertions.assertEquals(TransactionUIData().listOfYearMonth, originItem.data.listOfYearMonth)
            Assertions.assertEquals(TransactionUIData().transactionList, originItem.data.transactionList)

            //verify loading
            val loadingItem = awaitItem()
            Assertions.assertEquals(true, loadingItem.loading)
            Assertions.assertEquals(null, loadingItem.exception)
//
            //verify getDataFromDB - success
            val errorItem = awaitItem()
            Assertions.assertEquals(false, errorItem.loading)
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr,errorItem.exception?.message)

        }
    }
}