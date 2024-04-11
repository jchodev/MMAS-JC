package com.jerryalberto.mmas.feature.home.ui.data

import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.model.data.Transaction

data class HomeUiData(
    val type: AccountBalanceDataType = AccountBalanceDataType.TOTAL,
    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val latestTransaction: List<Transaction> = listOf(),
) {
    fun getTotalAmount () : Double {
        return totalIncome - totalExpenses
    }
}