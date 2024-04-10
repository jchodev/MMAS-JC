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
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import com.jerryalberto.mmas.core.common.result.Result

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
    fun getCountryListTest() {
        val result = settingUseCase.getCountryList()

        result.forEach {
            System.out.println(it)

            val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("",it ))
            format.setMinimumFractionDigits(2)
            format.setMaximumFractionDigits(2)
            System.out.println(format.format(1000000))

            System.out.println(format.format(-1000000))
        }
        System.out.println(DecimalFormat().decimalFormatSymbols.groupingSeparator)
        System.out.println(DecimalFormat().decimalFormatSymbols.decimalSeparator)
        System.out.println(DecimalFormat().decimalFormatSymbols.zeroDigit)
    }

    @Test
    fun `get setting with empty country`() = runTest {

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
    fun `get setting with empty country with flow`() = runTest {

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

}