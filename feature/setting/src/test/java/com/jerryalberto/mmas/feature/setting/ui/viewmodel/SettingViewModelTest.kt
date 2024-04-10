package com.jerryalberto.mmas.feature.setting.ui.viewmodel

import app.cash.turbine.test
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.domain.usecase.SettingUseCase
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.testing.data.SettingDataTestTubs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class SettingViewModelTest {

    private lateinit var viewModel : SettingViewModel
    private lateinit var settingUseCase: SettingUseCase
    private lateinit var transactionUseCase: TransactionUseCase

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        settingUseCase = mockkClass(SettingUseCase::class)
        transactionUseCase = mockkClass(TransactionUseCase::class)
        mockkStatic(Timber::class)

        viewModel = SettingViewModel(
            settingUseCase = settingUseCase,
            transactionUseCase = transactionUseCase,
        )
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `test SettingViewModel fetchSetting with success`() = runTest {
        //assign
        coEvery { settingUseCase.getSetting() } returns flowOf(SettingDataTestTubs.mockSetting)

        viewModel.fetchSettingState.test {
            //action
            viewModel.fetchSetting()

            //verify
            assertEquals(FetchSettingDataState.Loading, awaitItem())

            assertEquals(FetchSettingDataState.Success, awaitItem())
        }
    }

    @Test
    fun `test SettingViewModel clear data with success`() = runTest {
        //assign
        coEvery { transactionUseCase.deleteAllTransaction() } returns flowOf(Unit)

        viewModel.uiState.test {
            //action
            viewModel.clearData()

            //verify
            assertEquals(SettingUIState.Initial, awaitItem())
            assertEquals(SettingUIState.Loading, awaitItem())
            assertEquals(SettingUIState.Success, awaitItem())
        }
    }
}