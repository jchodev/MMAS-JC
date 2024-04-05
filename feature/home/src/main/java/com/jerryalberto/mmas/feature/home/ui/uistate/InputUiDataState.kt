package com.jerryalberto.mmas.feature.home.ui.uistate

import android.os.Parcelable
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionType
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputUiDataState(
    val type: TransactionType? = null,

    val description: String = "",
    val descriptionError: String? = null,

    val amount: Double? = null,
    val amountString: String = "",
    val amountError: String? = null,

    val date: Long? = null,
    val dateString: String = "",
    val dateError: String? = null,

    val hour: Int? = null,
    val minute: Int? = null,
    val timeString: String = "",
    val timeError: String? = null,

    val uri: String = "",

    val category: Category? = null,
    val categoryError: String? = null,

    ): Parcelable

fun InputUiDataState.asTransaction() = Transaction(
    type = type,
    category = category,
    amount = amount ?: 0.0,
    description = description ?: "",
    uri = uri ?: "",
    date = date ?: 0L,
    hour = hour ?: 0,
    minute = minute ?: 0
)