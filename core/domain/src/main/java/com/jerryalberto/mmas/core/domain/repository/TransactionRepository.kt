package com.jerryalberto.mmas.core.domain.repository

import com.jerryalberto.mmas.core.database.model.TransactionSummaryQueryResult
import com.jerryalberto.mmas.core.database.model.TransactionYearMonthQueryResult
import com.jerryalberto.mmas.core.model.data.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun deleteAllTransaction()
    suspend fun deleteTransactionById(id: Long)
    suspend fun getTransactionByDate(date: Long): Flow<List<Transaction>>

    suspend fun getAllTransaction(): Flow<List<Transaction>>
    suspend fun getAllTransactionGroupByDate(): Flow<Map<Triple<Int, Int, Int>, List<Transaction>>>
    suspend fun insertTransaction(transaction: Transaction): Long

    //main page
    suspend fun getGroupedAmountByDateRange(dateFrom: Long, dataTo: Long): Flow<List<TransactionSummaryQueryResult>>
    suspend fun getAllGroupedAmount(): Flow<List<TransactionSummaryQueryResult>>

    suspend fun getLatestTransaction(latest: Int): Flow<List<Transaction>>

    //all transaction page
    //header
    suspend fun getListOfYearMonth(): Flow<List<TransactionYearMonthQueryResult>>
    //list
    suspend fun getAllTransactionByYearMonth(year: Int, month: Int): Flow<Map<Long, List<Transaction>>>
}