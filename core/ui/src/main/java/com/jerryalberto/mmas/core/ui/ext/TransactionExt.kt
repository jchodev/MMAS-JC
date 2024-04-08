package com.jerryalberto.mmas.core.ui.ext

import androidx.compose.ui.graphics.Color

import com.jerryalberto.mmas.core.ui.constants.ColorConstant
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionType


fun Transaction.formatAmount(setting: Setting = Setting()): String {
    return when (this.type) {
        TransactionType.INCOME -> this.amount.formatAmount(
            setting = setting, withPlus = true
        )
        else -> (this.amount * -1).formatAmount(
            setting = setting
        )
    }
}

fun TransactionType?.getColors():  Pair<Color, Color> {
    return when (this) {
        TransactionType.INCOME -> ColorConstant.IncomeColors
        else -> ColorConstant.ExpensesColors
    }
}