package com.jerryalberto.mmas.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//ref: https://github.com/android/nowinandroid/blob/main/core/database/src/main/kotlin/com/google/samples/apps/nowinandroid/core/database/model/TopicEntity.kt
@Entity(
    tableName = "money_tbl",
)
data class MoneyEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,//last so that we don't have to pass an ID value or named arguments
    val type : String,
    val amount: Double,
    val category : String,
    val description  : String,
    val date: String,
    val time: String
)