package com.jerryalberto.mmas.core.datastore.model

import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.model.data.ThemeType
import com.jerryalberto.mmas.core.model.data.TimeFormatType
import kotlinx.serialization.Serializable

@Serializable
data class SettingPreference(
    val countryCode : String = "",
    val theme: String = "",
    val dateFormat: String = "",
    val use24HourFormat: Boolean = true,
)

fun SettingPreference.toSetting(): Setting {
    return Setting (
        countryCode = countryCode,
        themeType = ThemeType.fromValue(theme) ?: ThemeType.DEVICE_THEME,
        dateFormat = dateFormat,
        timeFormatType = TimeFormatType.fromValue(use24HourFormat) ?: TimeFormatType.HOUR_24
    )
}