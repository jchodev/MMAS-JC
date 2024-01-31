package com.jerryalberto.mmas.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jerryalberto.mmas.core.database.dao.MoneyDao
import com.jerryalberto.mmas.core.database.model.MoneyEntity

@Database(
    entities = [MoneyEntity::class],
    version = 1
)
internal abstract class MmasDatabase : RoomDatabase() {
    abstract fun moneyDao(): MoneyDao
}