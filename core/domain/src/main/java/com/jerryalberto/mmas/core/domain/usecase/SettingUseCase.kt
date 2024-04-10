package com.jerryalberto.mmas.core.domain.usecase

import com.jerryalberto.mmas.core.domain.repository.SettingPreferenceRepository

import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.model.data.ThemeType
import com.jerryalberto.mmas.core.model.data.TimeFormatType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
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
            if (setting.countryCode.isEmpty()) {
                Timber.d("SettingUseCase::getSetting()::get default")
                val defaultSetting = getDefaultSetting()
                saveSetting(defaultSetting)
                flowOf(defaultSetting)
            } else {
                Timber.d("SettingUseCase::getSetting()::have data")
                flowOf(setting)
            }
        }
    }

    //Country / Currency -----------
    fun getCountryList() : List<String> {
        return Locale.getISOCountries().toList()
    }

    //Date format ----------------------
    fun getDateFormatList() : List<String> {
        return listOf(
            "dd-MM-yyyy",
            "dd/MM/yyyy",
            "yyyy-MM-dd"
        )
    }

    //time format
    fun getTimeFormatList() : List<TimeFormatType> {
        return listOf(
            TimeFormatType.HOUR_12,
            TimeFormatType.HOUR_24,
        )
    }

    fun getThemeList(): List<ThemeType>{
        return listOf(
            ThemeType.DEVICE_THEME,
            ThemeType.DARK,
            ThemeType.LIGHT
        )
    }

    private fun getDefaultSetting(): Setting {
        return Setting(
            countryCode = Locale.getDefault().country,
            themeType = ThemeType.DEVICE_THEME,
            dateFormat = "yyyy-MM-dd",
            timeFormatType = TimeFormatType.HOUR_24,
        )
    }
}