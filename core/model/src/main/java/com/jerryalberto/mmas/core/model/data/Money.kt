package com.jerryalberto.mmas.core.model.data

data class Money(
    var id: Int = 0,//last so that we don't have to pass an ID value or named arguments
    val type : String,
    val amount: Double,
    val category : String,
    val description  : String,
    val uri: String,
    val date: String,
    val time: String
)