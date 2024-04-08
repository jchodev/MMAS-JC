package com.jerryalberto.mmas.core.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Locale

@Parcelize
data class Setting(
    val countryCode : String = "US",
    val theme: String = "",
    val dateFormat: String = "",
    val timeFormatType: TimeFormatType = TimeFormatType.HOUR_24,
) : Parcelable

