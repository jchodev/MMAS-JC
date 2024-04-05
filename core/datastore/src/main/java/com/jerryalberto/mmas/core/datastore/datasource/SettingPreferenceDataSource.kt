package com.jerryalberto.mmas.core.datastore.datasource

import com.jerryalberto.mmas.core.datastore.model.SettingPreference
import kotlinx.coroutines.flow.Flow

interface SettingPreferenceDataSource {
    suspend fun saveSettingPreference(settingPreference: SettingPreference)
    fun getSettingPreference(): Flow<SettingPreference>
}