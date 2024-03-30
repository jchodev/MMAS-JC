package com.jerryalberto.mmas.feature.transaction.model

import com.jerryalberto.mmas.core.model.data.Transaction

data class TransactionDate (
    val date: Long,
    val totalAmount: Double,
    val transactions: List<Transaction> = listOf(),
)