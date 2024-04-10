package com.jerryalberto.mmas.core.ui.navigation

sealed class MainRoute(val route: String) {
    data object HomeScreen : MainRoute("home_screen")
    data object TransactionScreen : MainRoute("transaction_screen")
    data object SettingScreen : MainRoute("setting_screen")
}