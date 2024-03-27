package com.jerryalberto.mmas.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionType

//ref: https://github.com/android/nowinandroid/blob/main/core/database/src/main/kotlin/com/google/samples/apps/nowinandroid/core/database/model/TopicEntity.kt
@Entity(
    tableName = "transaction_tbl",
)
data class TransactionEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,//last so that we don't have to pass an ID value or named arguments
    val type : String,
    val amount: Double,
    val category : String,
    val description  : String,
    val uri: String,
    val date: Long,
    val hour: Int,
    val minute: Int,
)

fun TransactionEntity.asExternalModel() = Transaction (
    id = id,
    type = TransactionType.valueOf(type),
    amount = amount,
    category = Category(type = CategoryType.valueOf(category)),
    description = description,
    uri = uri,
    date = date,
    hour = hour,
    minute = minute,
)