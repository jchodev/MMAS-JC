package com.jerryalberto.mmas.core.domain.usecase


import com.jerryalberto.mmas.core.domain.R
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType

class GetCategoriesUseCase {

    operator fun invoke() : List<Category> {
        return listOf(
            //food and beverage
            Category(
                type = CategoryType.FOOD_AND_BEVERAGES,
                imageResId = R.drawable.ic_food_and_beveages,
                stringResId = R.string.core_domain_food_and_beverages,
                items = listOf(
                    Category(
                        type = CategoryType.FOOD,
                        imageResId = R.drawable.ic_food,
                        stringResId = R.string.core_domain_food
                    ),
                    Category(
                        type = CategoryType.BEVERAGES,
                        imageResId = R.drawable.ic_beveages,
                        stringResId = R.string.core_domain_beverages
                    ),
                    Category(
                        type = CategoryType.GROCERIES,
                        imageResId = R.drawable.ic_groceries,
                        stringResId = R.string.core_domain_groceries
                    )
                )
            ),
            //TRANSPORT
            Category(
                type = CategoryType.TRANSPORT,
                imageResId = R.drawable.ic_transport,
                stringResId = R.string.core_domain_transport,
                items = listOf(
                    Category(
                        type = CategoryType.FUEL,
                        imageResId = R.drawable.ic_fuel,
                        stringResId = R.string.core_domain_fuel
                    ),
                    Category(
                        type = CategoryType.PARKING,
                        imageResId = R.drawable.ic_parking,
                        stringResId = R.string.core_domain_parking
                    ),
                    Category(
                        type = CategoryType.SERVICE_AND_MAINTENANCE,
                        imageResId = R.drawable.ic_maintenance,
                        stringResId = R.string.core_domain_service_and_maintenance
                    ),
                    Category(
                        type = CategoryType.TAXI,
                        imageResId = R.drawable.ic_taxi,
                        stringResId = R.string.core_domain_taxi
                    )
                )
            ),
            //Personal Development
            Category(
                type = CategoryType.PERSONAL_DEVELOPMENT,
                imageResId = R.drawable.ic_personal_development,
                stringResId = R.string.core_domain_personal_development,
                items = listOf(
                    Category(
                        type = CategoryType.BUSINESS,
                        imageResId = R.drawable.ic_business,
                        stringResId = R.string.core_domain_business
                    ),
                    Category(
                        type = CategoryType.EDUCATION,
                        imageResId = R.drawable.ic_education,
                        stringResId = R.string.core_domain_personal_education
                    ),
                    Category(
                        type = CategoryType.INVESTMENT,
                        imageResId = R.drawable.ic_investment,
                        stringResId = R.string.core_domain_investment
                    )
                )
            ),
            //Shopping
            Category(
                type = CategoryType.SHOPPING,
                imageResId = R.drawable.ic_shopping,
                stringResId = R.string.core_domain_shopping,
                items = listOf(
                    Category(
                        type = CategoryType.CLOTHES,
                        imageResId = R.drawable.ic_clothes,
                        stringResId = R.string.core_domain_cloths
                    ),
                    Category(
                        type = CategoryType.ACCESSORIES,
                        imageResId = R.drawable.ic_accessories,
                        stringResId = R.string.core_domain_accessories
                    ),
                    Category(
                        type = CategoryType.ELECTRONIC_DEVICE,
                        imageResId = R.drawable.ic_electronics,
                        stringResId = R.string.core_domain_electronics
                    )
                )
            ),
        )
    }
}