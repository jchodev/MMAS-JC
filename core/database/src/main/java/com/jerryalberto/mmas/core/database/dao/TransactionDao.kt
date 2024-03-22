package com.jerryalberto.mmas.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
        WHERE date = :date
        ORDER BY hour, minute
    """,
    )
    fun getTransactionByDate(date: Long): Flow<List<TransactionEntity>>

    @Query(
        value = """
        SELECT * FROM transaction_tbl
        ORDER BY date DESC, hour DESC, minute DESC
    """,
    )
    fun getAllTransaction(): Flow<List<TransactionEntity>>

    @Query("SELECT type, SUM(amount) as total_amount FROM transaction_tbl GROUP BY type")
    fun getSumAmountGroupedByType(): Flow<List<TransactionSummaryQueryResult>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity)

    @Query(
        value = """
            DELETE FROM transaction_tbl
            WHERE id = :id
        """,
    )
    suspend fun deleteTransactionById(id: Int)
}