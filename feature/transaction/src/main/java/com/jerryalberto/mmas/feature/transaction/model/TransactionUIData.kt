package com.jerryalberto.mmas.feature.transaction.model

import android.os.Parcelable
import com.jerryalberto.mmas.feature.transaction.ui.model.YearMonthItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionUIData (
    val listOfYearMonth: List<YearMonthItem> = emptyList(),
    val transactionList: List<TransactionGroup> = emptyList()
): Parcelable
