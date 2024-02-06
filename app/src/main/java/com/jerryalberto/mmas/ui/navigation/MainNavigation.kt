package com.jerryalberto.mmas.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jerryalberto.mmas.feature.analysis.ui.screen.AnalysisScreen
import com.jerryalberto.mmas.feature.calendar.ui.screen.CalendarScreen
import com.jerryalberto.mmas.feature.input.ui.screen.InputScreen
import com.jerryalberto.mmas.feature.setting.ui.screen.SettingScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MainActivityScreen.InputScreen.route
    ) {
        composable(MainActivityScreen.InputScreen.route) {
            InputScreen()
        }

        composable(MainActivityScreen.AnalysisScreen.route) {
            AnalysisScreen()
        }

        composable(MainActivityScreen.CalendarScreen.route) {
            CalendarScreen()
        }

        composable(MainActivityScreen.SettingScreen.route) {
            SettingScreen()
        }
    }
}


sealed class MainActivityScreen(val route: String) {

    data object InputScreen : MainActivityScreen("input_screen")
    data object AnalysisScreen : MainActivityScreen("analysis_screen")
    data object CalendarScreen : MainActivityScreen("calendar_screen")
    data object SettingScreen : MainActivityScreen("setting_screen")
}