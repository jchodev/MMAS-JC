package com.jerryalberto.mmas.core.datastore.model

import com.jerryalberto.mmas.core.model.data.ThemeType
import com.jerryalberto.mmas.core.model.data.TimeFormatType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SettingPreferenceTest {

    @Test
    fun `test SettingPreference toSetting success (use24HourFormat = true) `(){
        val mockSettingPreference = SettingPreference(
            countryCode = "HK",
            theme = "Dark",
            dateFormat = "yyyy",
            use24HourFormat = true
        )

        //action
        val result = mockSettingPreference.toSetting()

        //verify
        Assertions.assertEquals(mockSettingPreference.countryCode, result.countryCode)
        Assertions.assertEquals(ThemeType.DARK, result.themeType)
        Assertions.assertEquals(mockSettingPreference.dateFormat, result.dateFormat)
        Assertions.assertEquals(TimeFormatType.HOUR_24, result.timeFormatType)
    }

    @Test
    fun `test SettingPreference toSetting success (use24HourFormat = false) `(){
        val mockSettingPreference = SettingPreference(
            countryCode = "HK",
            theme = "Dark",
            dateFormat = "yyyy",
            use24HourFormat = false
        )

        //action
        val result = mockSettingPreference.toSetting()

        //verify
        Assertions.assertEquals(mockSettingPreference.countryCode, result.countryCode)
        Assertions.assertEquals(ThemeType.DARK, result.themeType)
        Assertions.assertEquals(mockSettingPreference.dateFormat, result.dateFormat)
        Assertions.assertEquals(TimeFormatType.HOUR_12, result.timeFormatType)
    }
}