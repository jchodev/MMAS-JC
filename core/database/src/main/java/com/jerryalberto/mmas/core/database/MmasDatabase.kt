package com.jerryalberto.mmas.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jerryalberto.mmas.core.database.dao.SettingDao
import com.jerryalberto.mmas.core.database.dao.TransactionDao
import com.jerryalberto.mmas.core.database.model.SettingEntity
import com.jerryalberto.mmas.core.database.model.TransactionEntity

@Database(
    entities = [TransactionEntity::class, SettingEntity::class],
    version = 1
)
internal abstract class MmasDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun settingDao(): SettingDao
}