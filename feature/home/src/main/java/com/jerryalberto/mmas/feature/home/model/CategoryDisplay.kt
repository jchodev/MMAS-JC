package com.jerryalberto.mmas.feature.home.model

import android.os.Parcelable
import com.jerryalberto.mmas.core.model.data.CategoryType
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDisplay (
    val type: CategoryType,
    val imageResId: Int = 0,
    val stringResId: Int = 0,
    val items: List<CategoryDisplay> = listOf()
): Parcelable
