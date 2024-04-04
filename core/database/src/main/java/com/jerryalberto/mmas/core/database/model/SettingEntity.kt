package com.jerryalberto.mmas.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.jerryalberto.mmas.core.model.data.Setting


@Entity(
    tableName = "setting_tbl",
)
data class SettingEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "country_code")
    var countryCode: String,
    var theme: String,
    @ColumnInfo(name = "date_format")
    var dateFormat: String,
    @ColumnInfo(name = "use_24")
    var use24HourFormat: Boolean = true,
)

fun SettingEntity.toSetting(): Setting {
    return Setting (
        id = id,
        countryCode = countryCode,
        theme = theme,
        dateFormat = dateFormat,
        use24HourFormat = use24HourFormat,
    )
}