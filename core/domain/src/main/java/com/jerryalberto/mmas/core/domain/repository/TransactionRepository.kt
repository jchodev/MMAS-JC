package com.jerryalberto.mmas.core.domain.repository

import com.jerryalberto.mmas.core.database.model.TransactionDateQueryResult
import com.jerryalberto.mmas.core.database.model.TransactionSummaryQueryResult
import com.jerryalberto.mmas.core.model.data.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun getTransactionByDate(date: Long): Flow<List<Transaction>>
    suspend fun getLatestTransaction(latest: Int): Flow<List<Transaction>>
    suspend fun getSumAmountGroupedByDateRange(dateFrom: Long, dataTo: Long): Flow<List<TransactionSummaryQueryResult>>
    suspend fun getSumAmountGrouped(): Flow<List<TransactionSummaryQueryResult>>

    suspend fun getAllTransaction(): Flow<List<Transaction>>
    suspend fun getTransactionDates(): Flow<List<TransactionDateQueryResult>>
    suspend fun getAllTransactionGroupByDate(): Flow<Map<Triple<Int, Int, Int>, List<Transaction>>>
    suspend fun insertTransaction(transaction: Transaction)
}