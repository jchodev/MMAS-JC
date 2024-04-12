package com.jerryalberto.mmas.core.domain.usecase


import app.cash.turbine.test
import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.domain.repository.SettingPreferenceRepository
import com.jerryalberto.mmas.core.model.data.Setting
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun

import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Locale
import com.jerryalberto.mmas.core.common.result.Result
import io.mockk.coVerify

class SettingUseCaseTest {

    private lateinit var settingUseCase: SettingUseCase
    @MockK
    private lateinit var settingPreferenceRepository: SettingPreferenceRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        settingUseCase = SettingUseCase(
            settingPreferenceRepository = settingPreferenceRepository
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `get SettingUseCase saveSetting success`() = runTest {
        coJustRun { settingPreferenceRepository.saveSetting(any()) }

        //action
        settingUseCase.saveSetting(Setting())

        coVerify(exactly = 1) { settingPreferenceRepository.saveSetting(any()) }
    }

    @Test
    fun `get SettingUseCase getSetting success`() = runTest {

        coJustRun { settingUseCase.saveSetting(any()) }

        //assign
        coEvery { settingPreferenceRepository.getSetting() } returns flowOf(Setting(
            countryCode = ""
        ))

        //action
        val actualSetting = settingUseCase.getSetting().first()

        //verify
        Assertions.assertEquals(actualSetting.countryCode, Locale.getDefault().country)
    }

    @Test
    fun `get SettingUseCase getSetting success(asResult)`() = runTest {
        coJustRun { settingUseCase.saveSetting(any()) }

        //assign
        coEvery { settingPreferenceRepository.getSetting() } returns flowOf(Setting(
            countryCode = ""
        ))

        //action and verify
        settingUseCase.getSetting().asResult()
            .test {
                assertEquals(Result.Loading, awaitItem())
                assertEquals(true, awaitItem() is Result.Success )
                awaitComplete()
            }
    }

    @Test
    fun `get SettingUseCase getCountryList success`() {
        val expectedResult = Locale.getISOCountries().toList()

        //action
        val actualResult = settingUseCase.getCountryList()
        //verify
        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `get SettingUseCase getTimeFormatList success`() {
        //action
        val actualResult = settingUseCase.getThemeList()
        //verify
        Assertions.assertEquals(3, actualResult.size)
    }

}