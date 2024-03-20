package com.jerryalberto.mmas.feature.home.ui.uistate
import android.os.Parcelable
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.feature.home.model.CategoryDisplay
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputUiDataState(
    val type: TransactionType? = null,
    val description: String = "",
    val amount: Double? = null,
    val amountString: String = "",
    val date: Long? = null,
    val dateString: String = "",
    val hour: Int? = null,
    val minute: Int? = null,
    val timeString: String = "",
    val uri: String ="",

    val category: CategoryDisplay? = null,
): Parcelable

fun InputUiDataState.asTransaction() = Transaction(
    type = type?.toString() ?: "",
    category = category?.toString() ?: "",
    amount = amount ?: 0.0,
    description = description ?: "",
    uri = uri,
    date = date ?: 0L,
    hour = hour ?: 0,
    minute = minute ?: 0
)