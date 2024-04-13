package com.jerryalberto.mmas.feature.home.ui.viewmodel

import app.cash.turbine.test
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import com.jerryalberto.mmas.feature.home.ui.data.HomeUiData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HomeScreenViewModelInitSuccessTest : BaseHomeScreenViewModelTest()  {

    @Test
    fun `test HomeScreenViewModel UIState when init(getDataFromDB) collect expected success`() = runTest {
        viewModel.uiState.test {
            //verify
            val originItem = awaitItem()
            Assertions.assertEquals(false,originItem.loading)
            Assertions.assertEquals(HomeUiData().type, originItem.data.type)
            Assertions.assertEquals(HomeUiData().totalIncome, originItem.data.totalIncome)
            Assertions.assertEquals(HomeUiData().totalExpenses, originItem.data.totalExpenses)
            Assertions.assertEquals(
                HomeUiData().latestTransaction.size,
                originItem.data.latestTransaction.size
            )

            val loadingItem = awaitItem()
            Assertions.assertEquals(true, loadingItem.loading)

            val successItem = awaitItem()
            Assertions.assertEquals(false,successItem.loading)
            Assertions.assertEquals(AccountBalanceDataType.TOTAL, successItem.data.type)
            Assertions.assertEquals(
                TransactionsDataTestTubs.transactionSummary.income,
                successItem.data.totalIncome
            )
            Assertions.assertEquals(
                TransactionsDataTestTubs.transactionSummary.expenses,
                successItem.data.totalExpenses
            )
            Assertions.assertEquals(transactionList, successItem.data.latestTransaction)

        }
    }


}