package com.jerryalberto.mmas.feature.home.ui.viewmodel

import app.cash.turbine.test
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.testing.data.ExceptionTestTubs
import com.jerryalberto.mmas.core.testing.data.TransactionsDataTestTubs
import com.jerryalberto.mmas.feature.home.ui.data.HomeUiData
import io.mockk.coEvery
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HomeScreenViewModelInitErrorTest : BaseHomeScreenViewModelTest()  {

    override fun assignInitResult(){
        coEvery { transactionUseCase.getLatestTransaction() } returns flow{
            throw ExceptionTestTubs.NormalException
        }
        coEvery { transactionUseCase.getSumAmountGroupedByType(AccountBalanceDataType.TOTAL) } returns flowOf(
            TransactionsDataTestTubs.transactionSummary
        )
    }

    @Test
    fun `test HomeScreenViewModel HomeUIState when init(getDataFromDB) collect expected error`() = runTest {
        viewModel.uiState.test {
            //verify
            Assertions.assertEquals(HomeUIState.Initial, awaitItem())
            Assertions.assertEquals(HomeUIState.Loading, awaitItem())
            when (val errorResult = awaitItem()) {
                is HomeUIState.Error -> {
                    Assertions.assertEquals(
                        ExceptionTestTubs.exceptionStr,
                        errorResult.exception.message,
                    )
                }
                else -> {}
            }
        }
    }

    @Test
    fun `test HomeScreenViewModel HomeUIDataState when init(getDataFromDB) collect expected error`() = runTest {
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

        }
    }

}