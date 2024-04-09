package com.jerryalberto.mmas.feature.transaction.ui.uistate

import com.jerryalberto.mmas.feature.transaction.model.TransactionGroup
import com.jerryalberto.mmas.feature.transaction.ui.model.YearMonthItem

data class TransactionUIDataState (
    val loading: Boolean = false,
    val listOfYearMonth: List<YearMonthItem> = emptyList(),
    val transactionList: List<TransactionGroup> = emptyList()
)

