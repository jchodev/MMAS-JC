package com.jerryalberto.mmas.core.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Setting(
    val countryCode : String = "US",
    val dateFormat: String = "",
    val timeFormatType: TimeFormatType = TimeFormatType.HOUR_24,
    val themeType: ThemeType = ThemeType.DEVICE_THEME,
) : Parcelable

