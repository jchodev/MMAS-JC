package com.jerryalberto.mmas.core.ui.ext

import java.text.SimpleDateFormat
import java.util.Date

fun Long.convertMillisToDate(dateFormat: String): String {
    val formatter = SimpleDateFormat(dateFormat)
    return formatter.format(Date(this))
}