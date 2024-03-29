package com.jerryalberto.mmas.core.ui.helper

import androidx.compose.ui.graphics.Color
import com.jerryalberto.mmas.core.designsystem.constant.ColorConstant
import com.jerryalberto.mmas.core.designsystem.utils.convertMillisToDate
import com.jerryalberto.mmas.core.model.data.TransactionType
import java.text.NumberFormat
import java.util.Calendar

class UiHelper {

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

//    fun formatAmount(amount: Double): String {
//        val numberFormat = NumberFormat.getNumberInstance()
//        numberFormat.minimumFractionDigits = 2
//        numberFormat.maximumFractionDigits = 2
//        return numberFormat.format(amount / 100)
//    }

    fun formatAmount(
            amount: Double,
            withPlus: Boolean = false
    ): String {
        val isNegative = amount < 0.0
        val amountForCalc = if (isNegative){
            amount * -1
        } else {
            amount
        }

        val numberFormat = NumberFormat.getNumberInstance()
        numberFormat.minimumFractionDigits = 2
        numberFormat.maximumFractionDigits = 2

        val formattedStr = "$" + numberFormat.format(amountForCalc / 100)

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

    fun formatAmount(amount: Double, type: TransactionType): String {
        val amountStr = formatAmount(amount)
        return if (type == TransactionType.EXPENSES)
            "- ".plus(amountStr)
        else
            "+ ".plus(amountStr)
    }

    fun displayDateTime(date: Long, hour: Int, minute:Int): String {
        val timeStr = displayHourMinute(hour = hour, minute = minute);
        return if (date == getCurrentDateDateMillis()){
            timeStr
        } else {
            timeStr.plus(" ").plus(date.convertMillisToDate("dd/MM/yyyy"))
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

    fun getColorByTransactionType(transactionType: TransactionType?): Pair<Color, Color> {
        var bgColor = Pair(ColorConstant.ExpensesRed, ColorConstant.ExpensesRedBg)
        if (transactionType == TransactionType.INCOME){
            bgColor = Pair(ColorConstant.IncomeGreen, ColorConstant.IncomeGreenBg)
        }
        //bgColor = Pair(ColorConstant.IncomeGreen, Color.White)
        return bgColor
    }
}