package com.jerryalberto.mmas.core.domain.usecase


import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType

class CategoriesUseCase {

    fun getExpenseCategories() : List<Category> {
        return listOf(
            //food and beverage
            Category(
                type = CategoryType.FOOD_AND_BEVERAGES,
                items = listOf(
                    Category(
                        type = CategoryType.FOOD,
                    ),
                    Category(
                        type = CategoryType.BEVERAGES,
                    ),
                    Category(
                        type = CategoryType.GROCERIES,
                    )
                )
            ),
            //TRANSPORT
            Category(
                type = CategoryType.TRANSPORT,
                items = listOf(
                    Category(
                        type = CategoryType.FUEL,
                    ),
                    Category(
                        type = CategoryType.PARKING,
                    ),
                    Category(
                        type = CategoryType.SERVICE_AND_MAINTENANCE,
                    ),
                    Category(
                        type = CategoryType.TAXI,
                    )
                )
            ),
            //Personal Development
            Category(
                type = CategoryType.PERSONAL_DEVELOPMENT,
                items = listOf(
                    Category(
                        type = CategoryType.BUSINESS,
                    ),
                    Category(
                        type = CategoryType.EDUCATION,
                    ),
                    Category(
                        type = CategoryType.INVESTMENT,
                    )
                )
            ),
            //Shopping
            Category(
                type = CategoryType.SHOPPING,
                items = listOf(
                    Category(
                        type = CategoryType.CLOTHES,
                    ),
                    Category(
                        type = CategoryType.ACCESSORIES,
                    ),
                    Category(
                        type = CategoryType.ELECTRONIC_DEVICE,
                    )
                )
            ),
            Category(
                type = CategoryType.OTHER,
            ),
        )
    }

    fun getIncomeCategories() : List<Category> {
        return listOf(
            Category(
                type = CategoryType.SALARY,
            ),
            Category(
                type = CategoryType.INVESTMENT,
            ),
            Category(
                type = CategoryType.BONUS,
            ),
            Category(
                type = CategoryType.SIDE_JOB,
            ),
            Category(
                type = CategoryType.GIFTS,
            ),
            Category(
                type = CategoryType.OTHER,
            ),
        )
    }
}