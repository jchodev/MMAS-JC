package com.jerryalberto.mmas.core.common.uistate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UIState<T : Parcelable>(
    val loading: Boolean = false,
    val data: T,
    val exception: Throwable? = null
) : Parcelable



