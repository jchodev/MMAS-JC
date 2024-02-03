package com.jerryalberto.mmas.core.data.repository

import com.jerryalberto.mmas.core.common.network.Dispatcher
import com.jerryalberto.mmas.core.common.network.MmasDispatchers
import com.jerryalberto.mmas.core.database.dao.MoneyDao
import com.jerryalberto.mmas.core.database.model.MoneyEntity
import com.jerryalberto.mmas.core.domain.repository.MoneyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@org.jetbrains.annotations.VisibleForTesting
class MoneyRepositoryImpl @Inject constructor(
    private val dao: MoneyDao,
    @Dispatcher(MmasDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): MoneyRepository {

    override suspend fun getMoneyByDate(date: String): List<MoneyEntity> {
        return withContext(ioDispatcher) {
             dao.getMoneyByDate(date = date)
        }
    }

}