package com.jerryalberto.mmas.core.datastore.model

import com.jerryalberto.mmas.core.model.data.Setting
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
        theme = theme,
        dateFormat = dateFormat,
        use24HourFormat = use24HourFormat,
    )
}