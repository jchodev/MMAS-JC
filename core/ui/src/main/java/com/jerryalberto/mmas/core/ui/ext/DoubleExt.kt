package com.jerryalberto.mmas.core.ui.ext

import com.jerryalberto.mmas.core.model.data.Setting
import java.text.NumberFormat
import java.util.Locale


fun Double.formatAmount(setting: Setting = Setting(), withCurrencySymbol:Boolean = true, withPlus: Boolean = false): String {
    //negative
    val isNegative = this < 0.0
    val amountForCalc = if (isNegative){
        this * -1
    } else {
        this
    }

    val format: NumberFormat = if (withCurrencySymbol) {
        NumberFormat.getCurrencyInstance(Locale("", setting.countryCode ))
    } else {
        NumberFormat.getInstance()
    }
    format.setMinimumFractionDigits(2)
    format.setMaximumFractionDigits(2)

    val formattedStr = format.format(amountForCalc / 100).replace(" ", " ")

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
