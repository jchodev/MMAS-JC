package com.jerryalberto.mmas.core.data.repository

import com.jerryalberto.mmas.core.common.network.Dispatcher
import com.jerryalberto.mmas.core.common.network.MmasDispatchers
import com.jerryalberto.mmas.core.data.model.asEntity
import com.jerryalberto.mmas.core.database.dao.TransactionDao
import com.jerryalberto.mmas.core.database.model.asExternalModel
import com.jerryalberto.mmas.core.domain.repository.TransactionRepository
import com.jerryalberto.mmas.core.model.data.Transaction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@org.jetbrains.annotations.VisibleForTesting
class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao,
    @Dispatcher(MmasDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): TransactionRepository {

    override suspend fun getTransactionByDate(date: Long): List<Transaction> {
        return withContext(ioDispatcher) {
             dao.getTransactionByDate(date = date).map {
                 it.asExternalModel()
             }
        }
    }

    override suspend fun getAllTransaction(): List<Transaction> {
        return withContext(ioDispatcher) {
            dao.getAllTransaction().map {
                it.asExternalModel()
            }
        }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.asEntity())
    }

}