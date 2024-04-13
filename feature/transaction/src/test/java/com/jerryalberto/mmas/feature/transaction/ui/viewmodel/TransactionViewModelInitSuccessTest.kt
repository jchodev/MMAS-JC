package com.jerryalberto.mmas.feature.transaction.ui.viewmodel

import app.cash.turbine.test
import com.jerryalberto.mmas.feature.transaction.model.TransactionUIData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TransactionViewModelInitSuccessTest: BaseTransactionViewModelTest() {

    @Test
    fun `test TransactionViewModel UIState when init(getDataFromDB) collect expected success`() = runTest {
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
            val successItem = awaitItem()
            val successData = successItem.data
            Assertions.assertEquals(true, successItem.loading)
            Assertions.assertEquals(null, successItem.exception)
            Assertions.assertEquals(exceptedYearMonthItemList, successData.listOfYearMonth)

            val getTransactionsByYearMonthSuccessItem = awaitItem()
            Assertions.assertEquals(false, getTransactionsByYearMonthSuccessItem.loading)
            Assertions.assertEquals(null, getTransactionsByYearMonthSuccessItem.exception)
            val getTransactionsByYearMonthSuccessData = getTransactionsByYearMonthSuccessItem.data
            getTransactionsByYearMonthSuccessData.transactionList.forEach {
                Assertions.assertEquals(allTransactionResult.get(it.date), it.transactions)
            }
        }
    }
}