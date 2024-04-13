package com.jerryalberto.mmas.feature.home.ui.data

import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.model.data.Transaction

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeUiData(
    val type: AccountBalanceDataType = AccountBalanceDataType.TOTAL,
    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val latestTransaction: List<Transaction> = listOf(),
) : Parcelable {
    fun getTotalAmount () : Double {
        return totalIncome - totalExpenses
    }
}