package com.jerryalberto.mmas.feature.home.ui.uistate

import com.jerryalberto.mmas.core.model.data.Transaction

data class HomeUIDataState(

    val loading: Boolean = false,

    val totalAmount: String = "0.00",
    val totalIncome: String = "0.00",
    val totalExpenses: String = "0.00",

    val latestTransaction: List<Transaction> = listOf()
)