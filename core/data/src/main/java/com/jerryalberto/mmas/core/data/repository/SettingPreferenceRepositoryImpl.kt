package com.jerryalberto.mmas.core.data.repository

import com.jerryalberto.mmas.core.common.network.Dispatcher
import com.jerryalberto.mmas.core.common.network.MmasDispatchers
import com.jerryalberto.mmas.core.data.model.toSettingPreference
import com.jerryalberto.mmas.core.datastore.datasource.SettingPreferenceDataSource
import com.jerryalberto.mmas.core.datastore.model.toSetting
import com.jerryalberto.mmas.core.domain.repository.SettingPreferenceRepository
import com.jerryalberto.mmas.core.model.data.Setting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SettingPreferenceRepositoryImpl @Inject constructor(
    private val settingPreferenceDataSource: SettingPreferenceDataSource,
    @Dispatcher(MmasDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): SettingPreferenceRepository {

    override suspend fun saveSetting(setting: Setting) {
        withContext(ioDispatcher){
            settingPreferenceDataSource.saveSettingPreference(
                setting.toSettingPreference()
            )
        }
    }

    override suspend fun getSetting(): Flow<Setting> {
        return withContext(ioDispatcher) {
            settingPreferenceDataSource.getSettingPreference().map {
                it.toSetting()
            }
        }
    }
}