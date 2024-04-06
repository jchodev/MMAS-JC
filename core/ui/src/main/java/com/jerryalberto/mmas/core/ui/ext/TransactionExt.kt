package com.jerryalberto.mmas.core.ui.ext

import androidx.compose.ui.graphics.Color

import com.jerryalberto.mmas.core.designsystem.constant.ColorConstant
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.model.data.Transaction

import com.jerryalberto.mmas.core.model.data.TransactionType
import java.util.Calendar


//fun Transaction.displayHourMinute(): String {
//    val stringResId = getStringResId(this)
//    return stringResource(stringResId)
//}

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
        TransactionType.INCOME -> Pair(ColorConstant.IncomeGreen, ColorConstant.IncomeGreenBg)
        else -> Pair(ColorConstant.ExpensesRed, ColorConstant.ExpensesRedBg)
    }
}

fun Transaction.displayDateTime(setting: Setting = Setting()): String {
    val timeStr = displayHourMinute(hour = hour, minute = minute);
    return if (date == getCurrentDateDateMillis()){
        timeStr
    } else {
        timeStr.plus(" ").plus(date.convertMillisToDate(setting.dateFormat))
    }
}


fun displayHourMinute(hour: Int, minute:Int): String {
    val formattedHour = if (hour <= 9) {
        "0$hour"
    } else {
        "$hour"
    }

    val formattedMinute = if (minute <= 9) {
        "0$minute"
    } else {
        "$minute"
    }

    return "$formattedHour:$formattedMinute"
}

private fun getCurrentDateDateMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}
