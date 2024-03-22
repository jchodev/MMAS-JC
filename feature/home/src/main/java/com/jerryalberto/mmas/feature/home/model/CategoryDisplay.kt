package com.jerryalberto.mmas.feature.home.model

import android.os.Parcelable
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.feature.home.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDisplay (
    val type: CategoryType,
    val imageResId: Int = 0,
    val stringResId: Int = 0,
    val items: List<CategoryDisplay> = listOf(),
): Parcelable

fun CategoryDisplay.toCategory(): Category = Category(
    type = type,
    items = items.map {
        it.toCategory()
    }
)

fun Category.toCategoryDisplay(): CategoryDisplay = CategoryDisplay(
    type = type,
    imageResId = getImageResId(type),
    stringResId = getStringResId(type),
    items = items.map { it.toCategoryDisplay() }
)

private fun getImageResId(categoryType: CategoryType): Int {
    return when (categoryType){
        CategoryType.FOOD_AND_BEVERAGES -> R.drawable.ic_food_and_beveages
        CategoryType.FOOD -> R.drawable.ic_food
        CategoryType.BEVERAGES -> R.drawable.ic_beveages
        CategoryType.GROCERIES -> R.drawable.ic_groceries

        CategoryType.TRANSPORT -> R.drawable.ic_transport
        CategoryType.FUEL -> R.drawable.ic_fuel
        CategoryType.PARKING -> R.drawable.ic_parking
        CategoryType.SERVICE_AND_MAINTENANCE -> R.drawable.ic_maintenance
        CategoryType.TAXI -> R.drawable.ic_taxi

        CategoryType.PERSONAL_DEVELOPMENT -> R.drawable.ic_personal_development
        CategoryType.BUSINESS -> R.drawable.ic_business
        CategoryType.EDUCATION -> R.drawable.ic_education
        CategoryType.INVESTMENT -> R.drawable.ic_investment

        CategoryType.SHOPPING -> R.drawable.ic_shopping
        CategoryType.CLOTHES -> R.drawable.ic_clothes
        CategoryType.ACCESSORIES -> R.drawable.ic_accessories
        CategoryType.ELECTRONIC_DEVICE -> R.drawable.ic_electronics
    }
}

private fun getStringResId(categoryType: CategoryType): Int {
    return when (categoryType){
        CategoryType.FOOD_AND_BEVERAGES -> R.string.feature_home_food_and_beverages
        CategoryType.FOOD -> R.string.feature_home_food
        CategoryType.BEVERAGES -> R.string.feature_home_beverages
        CategoryType.GROCERIES -> R.string.feature_home_groceries

        CategoryType.TRANSPORT -> R.string.feature_home_transport
        CategoryType.FUEL -> R.string.feature_home_fuel
        CategoryType.PARKING -> R.string.feature_home_parking
        CategoryType.SERVICE_AND_MAINTENANCE -> R.string.feature_home_service_and_maintenance
        CategoryType.TAXI -> R.string.feature_home_taxi

        CategoryType.PERSONAL_DEVELOPMENT -> R.string.feature_home_personal_development
        CategoryType.BUSINESS -> R.string.feature_home_business
        CategoryType.EDUCATION -> R.string.feature_home_personal_education
        CategoryType.INVESTMENT -> R.string.feature_home_investment

        CategoryType.SHOPPING -> R.string.feature_home_shopping
        CategoryType.CLOTHES -> R.string.feature_home_cloths
        CategoryType.ACCESSORIES -> R.string.feature_home_accessories
        CategoryType.ELECTRONIC_DEVICE -> R.string.feature_home_electronics
    }
}