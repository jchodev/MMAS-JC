package com.jerryalberto.mmas.core.ui.ext

import androidx.compose.ui.graphics.Color

import com.jerryalberto.mmas.core.designsystem.constant.ColorConstant
import com.jerryalberto.mmas.core.model.data.Transaction

import com.jerryalberto.mmas.core.model.data.TransactionType


//fun Transaction.displayHourMinute(): String {
//    val stringResId = getStringResId(this)
//    return stringResource(stringResId)
//}

fun Transaction.formatAmount(currencySymbol: String = "$"): String {
    return when (this.type) {
        TransactionType.INCOME -> this.amount.formatAmount(
            currencySymbol = currencySymbol, withPlus = true
        )
        else -> (this.amount * -1).formatAmount(
            currencySymbol = currencySymbol
        )
    }
}

fun TransactionType?.getColors():  Pair<Color, Color> {
    return when (this) {
        TransactionType.INCOME -> Pair(ColorConstant.IncomeGreen, ColorConstant.IncomeGreenBg)
        else -> Pair(ColorConstant.ExpensesRed, ColorConstant.ExpensesRedBg)
    }
}