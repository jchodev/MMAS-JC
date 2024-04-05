package com.jerryalberto.mmas.core.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.core.ui.R


@Composable
fun CategoryType.getString(): String {
    val stringResId = getStringResId(this)
    return stringResource(stringResId)
}

@Composable
fun CategoryType.getImageVector(): ImageVector {
    val imageResId = getImageResId(this)
    return ImageVector.vectorResource(imageResId)
}


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

        CategoryType.OTHER -> R.drawable.ic_other
        CategoryType.SALARY -> R.drawable.ic_salary
        CategoryType.BONUS -> R.drawable.ic_bonus
        CategoryType.SIDE_JOB -> R.drawable.ic_side_job
        CategoryType.GIFTS -> R.drawable.ic_gifts

    }
}

private fun getStringResId(categoryType: CategoryType): Int {
    return when (categoryType){
        CategoryType.FOOD_AND_BEVERAGES -> R.string.core_ui_food_and_beverages
        CategoryType.FOOD -> R.string.core_ui_food
        CategoryType.BEVERAGES -> R.string.core_ui_beverages
        CategoryType.GROCERIES -> R.string.core_ui_groceries

        CategoryType.TRANSPORT -> R.string.core_ui_transport
        CategoryType.FUEL -> R.string.core_ui_fuel
        CategoryType.PARKING -> R.string.core_ui_parking
        CategoryType.SERVICE_AND_MAINTENANCE -> R.string.core_ui_service_and_maintenance
        CategoryType.TAXI -> R.string.core_ui_taxi

        CategoryType.PERSONAL_DEVELOPMENT -> R.string.core_ui_personal_development
        CategoryType.BUSINESS -> R.string.core_ui_business
        CategoryType.EDUCATION -> R.string.core_ui_personal_education
        CategoryType.INVESTMENT -> R.string.core_ui_investment

        CategoryType.SHOPPING -> R.string.core_ui_shopping
        CategoryType.CLOTHES -> R.string.core_ui_cloths
        CategoryType.ACCESSORIES -> R.string.core_ui_accessories
        CategoryType.ELECTRONIC_DEVICE -> R.string.core_ui_electronics


        CategoryType.OTHER -> R.string.core_ui_others
        CategoryType.SALARY -> R.string.core_ui_salary
        CategoryType.BONUS -> R.string.core_ui_bonus
        CategoryType.SIDE_JOB -> R.string.core_ui_side_job
        CategoryType.GIFTS -> R.string.core_ui_gifts
    }
}