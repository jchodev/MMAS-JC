package com.jerryalberto.mmas.core.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Currency

@Parcelize
data class CountryData(
    var countryCode: String,
    val countryName: String,
    val countryNameEng: String,
    val countryFlag: String,
    val currency: Currency,
) : Parcelable
