package com.jerryalberto.mmas.core.model.data

data class Transaction(
    var id: Int = 0,//last so that we don't have to pass an ID value or named arguments
    val type : TransactionType? = null,
    val amount: Double,
    val category : Category?= null,
    val description  : String,
    val uri: String,
    val date: Long,
    val hour: Int,
    val minute: Int,
)

