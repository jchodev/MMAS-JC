package com.jerryalberto.mmas.core.domain.usecase

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SettingUseCaseTest {

    private lateinit var settingUseCase: SettingUseCase

    @BeforeEach
    fun setUp() {
        settingUseCase = SettingUseCase()
    }

    @Test
    fun getCountryListTest() {
        val result = settingUseCase.getCountryList()

        result.forEach {
            System.out.println(it)
        }
        System.out.println(result)
    }
}