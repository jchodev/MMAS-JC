package com.jerryalberto.mmas.core.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Setting(
    val countryCode : String = "",
    val theme: String = "",
    val dateFormat: String = "",
    val use24HourFormat: Boolean = true,
) : Parcelable

