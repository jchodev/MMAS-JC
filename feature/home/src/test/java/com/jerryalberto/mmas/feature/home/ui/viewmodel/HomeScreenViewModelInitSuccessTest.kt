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
    fun `test HomeScreenViewModel HomeUIState when init(getDataFromDB) collect expected success`() = runTest {
        viewModel.uiState.test {
            //verify
            Assertions.assertEquals(HomeUIState.Initial, awaitItem())
            Assertions.assertEquals(HomeUIState.Loading, awaitItem())
            Assertions.assertEquals(HomeUIState.Success, awaitItem())
        }
    }

    @Test
    fun `test HomeScreenViewModel HomeUIDataState when init(getDataFromDB) collect expected success`() = runTest {
        viewModel.uiDataState.test {

            val originalHomeUiData = awaitItem()
            //verify
            Assertions.assertEquals(HomeUiData().type, originalHomeUiData.type)
            Assertions.assertEquals(HomeUiData().totalIncome, originalHomeUiData.totalIncome)
            Assertions.assertEquals(HomeUiData().totalExpenses, originalHomeUiData.totalExpenses)
            Assertions.assertEquals(
                HomeUiData().latestTransaction.size,
                originalHomeUiData.latestTransaction.size
            )

            val actualHomeUiData = awaitItem()
            //verify
            Assertions.assertEquals(AccountBalanceDataType.TOTAL, actualHomeUiData.type)
            Assertions.assertEquals(
                TransactionsDataTestTubs.transactionSummary.income,
                actualHomeUiData.totalIncome
            )
            Assertions.assertEquals(
                TransactionsDataTestTubs.transactionSummary.expenses,
                actualHomeUiData.totalExpenses
            )
            Assertions.assertEquals(transactionList, actualHomeUiData.latestTransaction)
        }
    }

}