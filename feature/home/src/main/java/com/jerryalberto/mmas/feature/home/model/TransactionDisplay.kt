package com.jerryalberto.mmas.feature.home.model

data class TransactionDisplay (
    val amount: Double,
    val category: CategoryDisplay,
    val description: String,
    val uri: String,
    val date: Long,
    val hour: Int,
    val minute: Int,
)