package com.jerryalberto.mmas.core.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jerryalberto.mmas.core.model.data.CategoryType

import com.jerryalberto.mmas.core.model.data.CountryData
import com.jerryalberto.mmas.core.model.data.TimeFormatType
import com.jerryalberto.mmas.core.ui.R

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

@Composable
fun TimeFormatType.getString() : String{
    return when (this){
        TimeFormatType.HOUR_12 -> stringResource(R.string.core_ui_12_hour)
        else -> stringResource(R.string.core_ui_24_hour)
    }
}
