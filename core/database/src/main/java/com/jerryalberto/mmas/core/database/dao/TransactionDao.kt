package com.jerryalberto.mmas.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jerryalberto.mmas.core.database.model.TransactionEntity
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
    suspend fun getTransactionByDate(date: Long): List<TransactionEntity>

    @Query(
        value = """
        SELECT * FROM transaction_tbl
        ORDER BY date DESC, hour DESC, minute DESC
    """,
    )
    suspend fun getAllTransaction(): List<TransactionEntity>

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