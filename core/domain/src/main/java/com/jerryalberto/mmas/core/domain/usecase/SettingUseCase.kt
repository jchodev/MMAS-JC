package com.jerryalberto.mmas.core.domain.usecase

import com.jerryalberto.mmas.core.model.data.CountryData
import java.text.NumberFormat
import java.util.ArrayList
import java.util.Locale

class SettingUseCase {

    fun getCountryList() : List<CountryData> {
        return Locale.getISOCountries().map {
            getCountryDataByCountryCode(
                countryCode = it
            )
        }
    }

    private fun getCountryDataByCountryCode(countryCode: String): CountryData {
        val locale = Locale("", countryCode)
        val countryName = locale.getDisplayCountry(Locale.getDefault())
        val countryNameEng = locale.getDisplayCountry(Locale("en"))
        val numberFormat = NumberFormat.getCurrencyInstance(locale)
        val currency = numberFormat.currency

        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41
        val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
        val flag = (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))

        return CountryData(
            countryCode = countryCode,
            countryName = countryName,
            countryNameEng = countryNameEng,
            countryFlag = flag,
            currency = currency
        )
    }

    fun getDefaultCountry(countryCode: String = Locale.getDefault().country): CountryData {
        return getCountryDataByCountryCode(countryCode = countryCode)
    }
}