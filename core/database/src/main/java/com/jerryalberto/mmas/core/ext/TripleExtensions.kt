package com.jerryalberto.mmas.core.ext

import java.util.Calendar

fun Triple<Int, Int, Int>.convertToDateMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(this.first, this.second - 1, this.third, 0, 0, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}