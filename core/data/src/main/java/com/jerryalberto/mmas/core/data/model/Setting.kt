package com.jerryalberto.mmas.core.data.model

import com.jerryalberto.mmas.core.datastore.model.SettingPreference
import com.jerryalberto.mmas.core.model.data.Setting

fun Setting.toSettingPreference(): SettingPreference {
    return SettingPreference(
        countryCode = countryCode,
        theme = themeType?.value ?: "",
        dateFormat = dateFormat,
        use24HourFormat = timeFormatType.value,
    )
}