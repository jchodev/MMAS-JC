package com.jerryalberto.mmas.feature.home.ui.uistate
import android.os.Parcelable
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.feature.home.model.CategoryDisplay
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputUiDataState(

    val description: String = "",
    val amount: Double? = null,
    val amountString: String = "",
    val date: Long? = null,
    val dateString: String = "",
    val hour: Int? = null,
    val minute: Int? = null,
    val timeString: String = "",

    val category: CategoryDisplay? = null,
): Parcelable
