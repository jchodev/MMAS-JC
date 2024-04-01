package com.jerryalberto.mmas.feature.transaction.model

import android.os.Parcelable
import com.jerryalberto.mmas.core.model.data.Transaction
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionData (
    val date: Long = 0,
    val totalAmount: Double = 0.0,
    val transactions: List<Transaction> = listOf(),
) : Parcelable

