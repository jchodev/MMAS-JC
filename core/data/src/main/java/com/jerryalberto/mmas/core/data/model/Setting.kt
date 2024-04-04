package com.jerryalberto.mmas.core.data.model

import com.jerryalberto.mmas.core.database.model.SettingEntity
import com.jerryalberto.mmas.core.model.data.Setting

fun Setting.asEntity() : SettingEntity {
    return SettingEntity(
        id = id,
        countryCode = countryCode,
        theme = theme,
        dateFormat = dateFormat,
        use24HourFormat = use24HourFormat,
    )
}