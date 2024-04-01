package com.jerryalberto.mmas.core.ui.helper

import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UiHelperTest {

    private lateinit var uiHelper: UiHelper


    @BeforeEach
    fun setUp() {
        uiHelper = UiHelper()
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test formatAmount with amount larger than 0 with expected` (){
        val actual = uiHelper.formatAmount(100.0)
        Assertions.assertEquals("$1.00", actual)
    }

    @Test
    fun `test formatAmount with amount larger than 0 with plus with expected` (){
        val actual = uiHelper.formatAmount(100.0, withPlus = true)
        Assertions.assertEquals("+ $1.00", actual)
    }

    @Test
    fun `test formatAmount with amount smaller than 0 with expected` (){
        val actual = uiHelper.formatAmount(-100.0)
        Assertions.assertEquals("- $1.00", actual)
    }
}