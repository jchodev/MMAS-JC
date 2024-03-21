package com.jerryalberto.mmas.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jerryalberto.mmas.core.database.dao.TransactionDao
import com.jerryalberto.mmas.core.database.model.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1
)
internal abstract class MmasDatabase : RoomDatabase() {
    abstract fun moneyDao(): TransactionDao
}