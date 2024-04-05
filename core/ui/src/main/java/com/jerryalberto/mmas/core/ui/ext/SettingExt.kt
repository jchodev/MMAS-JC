package com.jerryalberto.mmas.core.ui.ext

import com.jerryalberto.mmas.core.model.data.CountryData

import java.text.NumberFormat
import java.util.Locale


fun String.toCountryData(): CountryData {
    val locale = Locale("", this)
    val countryName = locale.getDisplayCountry(Locale.getDefault())
    val countryNameEng = locale.getDisplayCountry(Locale("en"))
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    val currency = numberFormat.currency

    val flagOffset = 0x1F1E6
    val asciiOffset = 0x41
    val firstChar = Character.codePointAt(this, 0) - asciiOffset + flagOffset
    val secondChar = Character.codePointAt(this, 1) - asciiOffset + flagOffset
    val flag = (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))

    return CountryData(
        countryCode = this,
        countryName = countryName,
        countryNameEng = countryNameEng,
        countryFlag = flag,
        currency = currency
    )
}