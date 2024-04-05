package com.jerryalberto.mmas.core.domain.usecase

import com.jerryalberto.mmas.core.domain.repository.SettingRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SettingUseCaseTest {

    private lateinit var settingUseCase: SettingUseCase
    @MockK
    private lateinit var settingRepository: SettingRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        settingUseCase = SettingUseCase(
            settingRepository = settingRepository
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
        }
        System.out.println(result)
    }
}