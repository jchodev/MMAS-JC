package com.jerryalberto.mmas.feature.home.ui.uistate

import android.os.Parcelable
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionType
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputUiDataState(

    val transaction: Transaction = Transaction(),

    val descriptionError: String? = null,
    val amountString: String = "",
    val amountError: String? = null,
    val dateError: String? = null,
    val timeError: String? = null,
    val categoryError: String? = null,
): Parcelable
