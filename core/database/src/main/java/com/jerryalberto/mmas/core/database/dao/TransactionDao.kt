package com.jerryalberto.mmas.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jerryalberto.mmas.core.database.model.TransactionDateQueryResult
import com.jerryalberto.mmas.core.database.model.TransactionEntity
import com.jerryalberto.mmas.core.database.model.TransactionSummaryQueryResult
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query(
        value = """
        SELECT * FROM transaction_tbl
        WHERE id = :id
    """,
    )
    fun getTransactionById(id: Int): Flow<TransactionEntity>

    @Query(
        value = """
        SELECT * FROM transaction_tbl
        WHERE year = :year
        AND month = :month
        AND day = :day
        ORDER BY hour DESC, minute DESC
    """,
    )
    fun getTransactionByDate(year: Int, month: Int, day: Int): Flow<List<TransactionEntity>>

    @Query(
        value = """
        SELECT * FROM transaction_tbl
        ORDER BY year DESC, month DESC, day DESC, hour DESC, minute DESC
    """,
    )
    fun getAllTransaction(): Flow<List<TransactionEntity>>

    @Query(
        value = """
        SELECT * FROM transaction_tbl
        ORDER BY year DESC, month DESC, day DESC, hour DESC, minute DESC
        LIMIT :latest
    """,
    )
    fun getLastTTransactionsByLimit(latest: Int): Flow<List<TransactionEntity>>


    @Query(
        value = """
        SELECT type, SUM(amount) as total_amount 
        FROM transaction_tbl 
        WHERE year >= :yearFrom AND month >= :monthFrom AND day >= :dayFrom
        AND year <= :yearTo AND month <= :monthTo AND day <= :dayTo
        GROUP BY type
    """,
    )
    fun getSumAmountGroupedByDateRange(yearFrom: Int, monthFrom: Int, dayFrom: Int, yearTo: Int, monthTo: Int, dayTo: Int): Flow<List<TransactionSummaryQueryResult>>

    @Query(
        value = """
        SELECT type, SUM(amount) as total_amount 
        FROM transaction_tbl
        GROUP BY type
    """,
    )
    fun getSumAmountGrouped(): Flow<List<TransactionSummaryQueryResult>>

    @Query(
        value = """
        SELECT year, month, day
        FROM transaction_tbl 
        GROUP BY year, month, day        
        ORDER BY year DESC, month DESC, day DESC 
    """,
    )
    fun getTransactionDates(): Flow<List<TransactionDateQueryResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity)

    @Query(
        value = """
            DELETE FROM transaction_tbl
            WHERE id = :id
        """,
    )
    suspend fun deleteTransactionById(id: Int)

    @Query(
        value = """
            DELETE FROM transaction_tbl
        """,
    )
    suspend fun deleteAllTransaction()
}