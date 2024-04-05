package com.jerryalberto.mmas.core.ui.ext

import java.text.NumberFormat


fun Double.formatAmount(currencySymbol: String = "$", withPlus: Boolean = false): String {
    //negative
    val isNegative = this < 0.0
    val amountForCalc = if (isNegative){
        this * -1
    } else {
        this
    }

    val numberFormat = NumberFormat.getNumberInstance()
    numberFormat.minimumFractionDigits = 2
    numberFormat.maximumFractionDigits = 2

    val formattedStr = currencySymbol+ numberFormat.format(amountForCalc / 100)

    return if (isNegative) {
        "- ".plus(formattedStr)
    } else {
        if (withPlus){
            "+ ".plus(formattedStr)
        } else {
            formattedStr
        }
    }
}
