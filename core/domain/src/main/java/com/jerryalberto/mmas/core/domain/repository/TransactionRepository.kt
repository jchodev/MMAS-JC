package com.jerryalberto.mmas.core.domain.repository


import com.jerryalberto.mmas.core.database.model.TransactionSummaryQueryResult
import com.jerryalberto.mmas.core.model.data.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun getTransactionByDate(date: Long): Flow<List<Transaction>>

    suspend fun getLatestTransaction(latest: Int = 4): Flow<List<Transaction>>
    suspend fun getSumAmountGroupedByType(): Flow<List<TransactionSummaryQueryResult>>

    suspend fun getAllTransaction(): Flow<List<Transaction>>

    suspend fun insertTransaction(transaction: Transaction)

}