package com.jerryalberto.mmas.core.data.repository

import com.jerryalberto.mmas.core.common.network.Dispatcher
import com.jerryalberto.mmas.core.common.network.MmasDispatchers
import com.jerryalberto.mmas.core.data.model.asEntity
import com.jerryalberto.mmas.core.database.dao.TransactionDao
import com.jerryalberto.mmas.core.database.model.TransactionDateQueryResult
import com.jerryalberto.mmas.core.database.model.TransactionEntity
import com.jerryalberto.mmas.core.database.model.TransactionSummaryQueryResult
import com.jerryalberto.mmas.core.database.model.asExternalModel
import com.jerryalberto.mmas.core.domain.repository.TransactionRepository
import com.jerryalberto.mmas.core.ext.convertMillisToYearMonthDay
import com.jerryalberto.mmas.core.model.data.Transaction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlin.collections.groupBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@org.jetbrains.annotations.VisibleForTesting
class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao,
    @Dispatcher(MmasDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): TransactionRepository {

    override suspend fun getTransactionByDate(date: Long): Flow<List<Transaction>> {
        return withContext(ioDispatcher) {
            val triple = date.convertMillisToYearMonthDay()
            dao.getTransactionByDate(year = triple.first, month = triple.second, day = triple.third)
                .map {
                    it.map(TransactionEntity::asExternalModel)
                }
        }
    }

    override suspend fun getAllTransaction(): Flow<List<Transaction>> {
        return withContext(ioDispatcher) {
            dao.getAllTransaction().map {
                it.map(TransactionEntity::asExternalModel)
            }
        }
    }

    override suspend fun getTransactionDates(): Flow<List<TransactionDateQueryResult>> {
        return withContext(ioDispatcher) {
            dao.getTransactionDates()
        }
    }

    override suspend fun getAllTransactionGroupByDate(): Flow<Map<Triple<Int, Int, Int>, List<Transaction>>> {
        return withContext(ioDispatcher) {
            dao.getAllTransaction().map {transitions ->
                transitions.groupBy {
                    Triple(it.year, it.month, it.day)
                }.mapValues { (_,transactionsList) -> transactionsList.map(TransactionEntity::asExternalModel) }
            }
        }
    }

    override suspend fun getLatestTransaction(latest: Int): Flow<List<Transaction>> {
        return withContext(ioDispatcher) {
            dao.getLastTTransactionsByLimit(latest = latest).map {
                it.map(TransactionEntity::asExternalModel)
            }
        }
    }

    override suspend fun getSumAmountGroupedByDateRange(dateFrom: Long, dataTo: Long): Flow<List<TransactionSummaryQueryResult>> {
        return withContext(ioDispatcher) {
            val from = dateFrom.convertMillisToYearMonthDay()
            val to = dataTo.convertMillisToYearMonthDay()
            dao.getSumAmountGroupedByDateRange(
                yearFrom = from.first,
                monthFrom = from.second,
                dayFrom = from.third,
                yearTo = to.first,
                monthTo = to.second,
                dayTo = to.third,
            )
        }
    }

    override suspend fun getSumAmountGrouped():  Flow<List<TransactionSummaryQueryResult>> {
        return withContext(ioDispatcher) {
            dao.getSumAmountGrouped()
        }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.asEntity())
    }

}