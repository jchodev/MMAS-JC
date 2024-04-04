package com.jerryalberto.mmas.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jerryalberto.mmas.core.database.model.SettingEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface SettingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSetting(settingEntity: SettingEntity): Long

    @Query(
        value = """
        SELECT * FROM setting_tbl
        LIMIT 1
    """,
    )
    fun getSetting(): Flow<SettingEntity?>
}