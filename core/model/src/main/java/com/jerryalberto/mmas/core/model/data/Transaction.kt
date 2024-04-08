package com.jerryalberto.mmas.core.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id: Long = 0,
    val type : TransactionType? = null,
    val amount: Double = 0.0,
    val category : Category?= null,
    val description  : String = "",
    val uri: String = "",
    val date: Long? = null,
    val hour: Int? = null,
    val minute: Int? = null,
) : Parcelable

