package com.jerryalberto.mmas.feature.transaction.model

import com.jerryalberto.mmas.feature.transaction.ui.model.YearMonthItem

data class TransactionUIData (
    val listOfYearMonth: List<YearMonthItem> = emptyList(),
    val transactionList: List<TransactionGroup> = emptyList()
)
