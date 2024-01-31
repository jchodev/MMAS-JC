package com.jerryalberto.mmas.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jerryalberto.mmas.core.database.model.MoneyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneyDao {

    @Query(
        value = """
        SELECT * FROM money_tbl
        WHERE id = :id
    """,
    )
    fun getMoneyById(id: Int): Flow<MoneyEntity>

    @Query(
        value = """
        SELECT * FROM money_tbl
        WHERE date = :date
        ORDER BY time
    """,
    )
    suspend fun getMoneyByDate(date: String): List<MoneyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoney(moneyEntity: MoneyEntity)

    @Query(
        value = """
            DELETE FROM money_tbl
            WHERE id = :id
        """,
    )
    suspend fun deleteMoneyById(id: Int)
}