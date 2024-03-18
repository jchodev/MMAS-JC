package com.jerryalberto.mmas.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jerryalberto.mmas.core.model.data.Money

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
    val uri: String,
    val date: String,
    val time: String,
)

fun MoneyEntity.asExternalModel() = Money (
    id = id,
    type = type,
    amount = amount,
    category = category,
    description = description,
    uri = uri,
    date = date,
    time = time,
)