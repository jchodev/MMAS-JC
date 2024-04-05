package com.jerryalberto.mmas.core.domain.usecase

import com.jerryalberto.mmas.core.domain.repository.SettingPreferenceRepository

import com.jerryalberto.mmas.core.model.data.CountryData
import com.jerryalberto.mmas.core.model.data.Setting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class SettingUseCase @Inject constructor(
    private val settingPreferenceRepository: SettingPreferenceRepository
) {

    suspend fun saveSetting(setting: Setting){
        Timber.d("SettingUseCase::saveSetting!!${setting}")
        return settingPreferenceRepository.saveSetting(setting = setting)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getSetting(): Flow<Setting> {

        return settingPreferenceRepository.getSetting().flatMapLatest { setting->
            Timber.d("SettingUseCase::getSetting():${setting}")
            if (setting.countryCode.isEmpty()){
                val defaultSetting = getDefaultSetting()
                saveSetting(defaultSetting)
                flowOf(defaultSetting)
            } else {
                flowOf(setting)
            }
        }
    }

    //Country / Currency -----------
    fun getCountryList() : List<String> {
        return Locale.getISOCountries().toList()
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

    //Date format ----------------------
    fun getDateFormatList() : List<String> {
        return listOf(
            "dd-MM-yyyy",
            "dd MMM yyyy",
            "dd/MM/yyyy",
            "yyyy-MM-dd"
        )
    }

    private fun getDefaultSetting(): Setting {
        return Setting(
            countryCode = Locale.getDefault().country,
            theme = "Dark",
            dateFormat = "yyyy-MM-dd",
            use24HourFormat = true,
        )
    }
}