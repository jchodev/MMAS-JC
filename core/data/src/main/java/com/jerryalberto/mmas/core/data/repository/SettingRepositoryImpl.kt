package com.jerryalberto.mmas.core.data.repository

import com.jerryalberto.mmas.core.common.network.Dispatcher
import com.jerryalberto.mmas.core.common.network.MmasDispatchers
import com.jerryalberto.mmas.core.data.model.asEntity
import com.jerryalberto.mmas.core.database.dao.SettingDao
import com.jerryalberto.mmas.core.database.model.toSetting
import com.jerryalberto.mmas.core.domain.repository.SettingRepository
import com.jerryalberto.mmas.core.model.data.Setting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val dao: SettingDao,
    @Dispatcher(MmasDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): SettingRepository {

    override suspend fun insertSetting(setting: Setting) {
        withContext(ioDispatcher) {
            dao.insertSetting(setting.asEntity())
        }
    }

    override suspend fun getSetting(): Flow<Setting?> {
        return withContext(ioDispatcher) {
            dao.getSetting().map { it?.toSetting() }
        }
    }

}