package com.jerryalberto.mmas.core.domain.repository


import com.jerryalberto.mmas.core.model.data.Setting
import kotlinx.coroutines.flow.Flow


interface SettingPreferenceRepository {

    suspend fun saveSetting(setting: Setting)
    suspend fun getSetting(): Flow<Setting>

}