package com.jerryalberto.mmas.feature.home.ui.data

import android.os.Parcelable
import com.jerryalberto.mmas.core.model.data.Transaction
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputTransactionData(

    val transaction: Transaction = Transaction(),

    val isSuccess: Boolean = false,

    val descriptionError: String? = null,
    val amountString: String = "",
    val amountError: String? = null,
    val dateError: String? = null,
    val timeError: String? = null,
    val categoryError: String? = null,
): Parcelable
