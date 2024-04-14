package com.jerryalberto.mmas.core.model.data

enum class TimeFormatType(val value: Boolean) {
    HOUR_24(true),
    HOUR_12(false);

    companion object {
        fun fromValue(value: Boolean): TimeFormatType? {
            return TimeFormatType.entries.find { it.value == value }
        }
    }
}