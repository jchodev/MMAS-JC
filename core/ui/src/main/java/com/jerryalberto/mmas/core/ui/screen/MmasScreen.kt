package com.jerryalberto.mmas.core.ui.screen

sealed class MmasScreen(val route: String) {
    data object HomeScreen : MmasScreen("home_screen")
    data object InputScreen : MmasScreen("input_screen")
    data object AnalysisScreen : MmasScreen("analysis_screen")
    data object CalendarScreen : MmasScreen("calendar_screen")
    data object SettingScreen : MmasScreen("setting_screen")
    data object TransactionScreen : MmasScreen("transaction_screen")

    data object SearchScreen: MmasScreen("search_screen")
}