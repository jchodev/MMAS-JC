package com.jerryalberto.mmas.core.ui.navigation

sealed class AppRoute(val route: String) {
    data object MainScreen : AppRoute("main_screen")
    data object InputScreen : AppRoute("input_screen")
    data object SearchScreen : AppRoute("search_screen")
}

sealed class MainRoute(val route: String) {
    data object HomeScreen : MainRoute("home_screen")
    data object TransactionScreen : MainRoute("transaction_screen")
    data object SettingScreen : MainRoute("setting_screen")
}