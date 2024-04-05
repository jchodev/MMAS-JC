package com.jerryalberto.mmas.core.domain.usecase

import com.jerryalberto.mmas.core.domain.repository.SettingPreferenceRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

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

}