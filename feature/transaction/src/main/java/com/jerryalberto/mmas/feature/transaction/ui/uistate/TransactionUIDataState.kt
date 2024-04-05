package com.jerryalberto.mmas.feature.transaction.ui.uistate

import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.feature.transaction.model.TransactionData
import com.jerryalberto.mmas.feature.transaction.ui.model.YearMonthItem

data class TransactionUIDataState (
    val loading: Boolean = false,
    val setting: Setting? = null,
    val listOfYearMonth: List<YearMonthItem> = emptyList(),
    val transactionList: List<TransactionData> = emptyList()
)

