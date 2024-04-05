package com.jerryalberto.mmas.feature.home.ui.uistate

import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.feature.home.ui.component.MultiFabState

data class HomeUIDataState(

    val loading: Boolean = false,

    val type: AccountBalanceDataType = AccountBalanceDataType.TOTAL,

    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,

    val latestTransaction: List<Transaction> = listOf(),

    val multiFabState: MultiFabState = MultiFabState.COLLAPSED
) {
    fun getTotalAmount () : Double {
        return totalIncome - totalExpenses
    }
}