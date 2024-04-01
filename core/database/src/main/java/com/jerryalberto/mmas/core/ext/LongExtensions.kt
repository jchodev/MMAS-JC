package com.jerryalberto.mmas.core.ext


import java.util.Calendar


fun Long.convertMillisToYearMonthDay(): Triple<Int, Int, Int> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // Month is zero-based, so add 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return Triple(year, month, day)
}