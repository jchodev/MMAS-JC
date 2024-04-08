package com.jerryalberto.mmas.core.model.data

enum class ThemeType(val value: String) {
    LIGHT("Light"),
    DARK("Dark"),
    DEVICE_THEME("Device Theme");

    companion object {
        fun fromValue(value: String): ThemeType? {
            return entries.find { it.value == value }
        }
    }
}