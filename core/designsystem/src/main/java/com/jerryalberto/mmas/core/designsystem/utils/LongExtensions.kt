package com.jerryalberto.mmas.core.designsystem.utils

import java.text.SimpleDateFormat
import java.util.Date

fun Long.convertMillisToDate(dateFormat: String): String {
    val formatter = SimpleDateFormat(dateFormat)
    return formatter.format(Date(this))
}