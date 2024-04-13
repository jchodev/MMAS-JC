package com.jerryalberto.mmas.feature.transaction.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class YearMonthItem (
    val year: Int,
    val month: Int,
    val selected: Boolean = false
): Parcelable