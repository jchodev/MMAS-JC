package com.jerryalberto.mmas.core.database.model

import androidx.room.ColumnInfo

data class TransactionSummaryQueryResult(
    val type: String,
    @ColumnInfo(name = "total_amount")
    val totalAmount: Double
)