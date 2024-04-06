package com.jerryalberto.mmas.core.ui.ext

import com.jerryalberto.mmas.core.model.data.Setting

fun Pair<Int, Int>.formatTime(setting: Setting = Setting()): String{
    val hour = first
    val minute = second
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