package com.jerryalberto.mmas.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jerryalberto.mmas.feature.analysis.ui.screen.AnalysisScreen
import com.jerryalberto.mmas.feature.calendar.ui.screen.CalendarScreen
import com.jerryalberto.mmas.feature.input.ui.screen.InputScreen
import com.jerryalberto.mmas.feature.setting.ui.screen.SettingScreen
import com.jerryalberto.mmas.feature.home.ui.screen.HomeScreen
import com.jerryalberto.mmas.feature.home.ui.viewmodel.HomeScreenViewModel
import com.jerryalberto.mmas.feature.transaction.ui.screen.TransactionScreen
import com.jerryalberto.mmas.feature.transaction.ui.viewmodel.TransactionViewModel

@Composable
fun MainNavigation(
    homeScreenViewModel: HomeScreenViewModel,
    transactionViewModel: TransactionViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainActivityScreen.HomeScreen.route
    ) {

        composable(MainActivityScreen.HomeScreen.route) {
            HomeScreen(homeScreenViewModel = homeScreenViewModel)
        }

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

        composable(MainActivityScreen.TransactionScreen.route) {
            TransactionScreen(viewModel = transactionViewModel)
        }
    }
}


sealed class MainActivityScreen(val route: String) {
    data object HomeScreen : MainActivityScreen("home_screen")
    data object InputScreen : MainActivityScreen("input_screen")
    data object AnalysisScreen : MainActivityScreen("analysis_screen")
    data object CalendarScreen : MainActivityScreen("calendar_screen")
    data object SettingScreen : MainActivityScreen("setting_screen")
    data object TransactionScreen : MainActivityScreen("transaction_screen")
}