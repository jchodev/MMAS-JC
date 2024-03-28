package com.jerryalberto.mmas.core.model.data

data class Category (
    val type: CategoryType,
    val items: List<Category> = listOf()
)

enum class CategoryType(val value: String){
    //Expenses
    FOOD_AND_BEVERAGES("FOOD_AND_BEVERAGES"),
    FOOD("FOOD"),
    BEVERAGES("BEVERAGES"),
    GROCERIES("GROCERIES"),

    TRANSPORT("TRAN=SPORT"),
    FUEL("FUEL"),
    PARKING("PARKING"),
    SERVICE_AND_MAINTENANCE("SERVICE_AND_MAINTENANCE"),
    TAXI("TAXI"),

    PERSONAL_DEVELOPMENT ("PERSONAL_DEVELOPMENT"),
    BUSINESS("BUSINESS"),
    EDUCATION("EDUCATION"),
    INVESTMENT("INVESTMENT"),

    SHOPPING("SHOPPING"),
    CLOTHES("CLOTHES"),
    ACCESSORIES("ACCESSORIES"),
    ELECTRONIC_DEVICE("ELECTRONIC_DEVICE"),

    OTHER("OTHERS"),

    //income
    SALARY("SALARY"),
    BONUS("BONUS"),
    SIDE_JOB("SIDE_JOB"),
    GIFTS("GIFTS"),

}
