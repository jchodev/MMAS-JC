package com.jerryalberto.mmas.core.ui.ext

import androidx.compose.ui.graphics.Color

import com.jerryalberto.mmas.core.ui.constants.ColorConstant
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.model.data.TimeFormatType
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


fun Long.convertMillisToDate(dateFormat: String): String {
    val formatter = SimpleDateFormat(dateFormat)
    return formatter.format(Date(this))
}

fun Transaction.displayDateTime(setting: Setting = Setting(), showTimeOnly: Boolean = true): String {
    val timeStr = displayHourMinute(setting = setting)
    return if (showTimeOnly || date == getCurrentDateDateMillis()){
        timeStr
    } else {
        date?.convertMillisToDate(setting.dateFormat).plus(" ").plus(timeStr)
    }
}

fun Transaction.formatAmount(setting: Setting = Setting(), withPlus: Boolean = true): String {
    return when (this.type) {
        TransactionType.INCOME -> this.amount.formatAmount(
            setting = setting, withPlus = withPlus
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

fun Transaction.displayHourMinute(setting: Setting = Setting()): String {
    if (hour == null || minute == null) {
        return ""
    }
    val is12Hour = setting.timeFormatType == TimeFormatType.HOUR_12
    val formattedHour = if (is12Hour) {
        when {
            hour == 0 -> 12
            hour!! > 12 -> hour!! - 12
            else -> hour
        }
    } else {
        hour
    }

    val period = if (is12Hour) {
        if (hour!! < 12) "AM" else "PM"
    } else {
        ""
    }

    val formattedMinute = if (minute!! < 10) "0$minute" else minute.toString()

    return if (is12Hour) {
        "$formattedHour:$formattedMinute $period"
    } else {
        "$formattedHour:$formattedMinute"
    }
}

private fun getCurrentDateDateMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}