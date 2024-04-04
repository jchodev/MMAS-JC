package com.jerryalberto.mmas.core.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Setting(
    val id: Long = 0,
    val countryCode : String = "",
    val countryData: CountryData? = null,
    val theme: String = "",
    val dateFormat: String = "",
    val use24HourFormat: Boolean = true,
) : Parcelable

