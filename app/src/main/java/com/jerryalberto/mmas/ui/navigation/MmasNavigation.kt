package com.jerryalberto.mmas.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jerryalberto.mmas.feature.analysis.ui.screen.AnalysisScreen
import com.jerryalberto.mmas.feature.calendar.ui.screen.CalendarScreen
import com.jerryalberto.mmas.feature.home.ui.screen.InputScreen
import com.jerryalberto.mmas.feature.setting.ui.screen.SettingScreen
import com.jerryalberto.mmas.feature.home.ui.screen.HomeScreen
import com.jerryalberto.mmas.feature.home.ui.viewmodel.HomeScreenViewModel
import com.jerryalberto.mmas.feature.transaction.ui.screen.TransactionScreen
import com.jerryalberto.mmas.core.ui.screen.MmasScreen
import com.jerryalberto.mmas.feature.transaction.model.TransactionData
import com.jerryalberto.mmas.feature.transaction.ui.screen.SearchScreen

@Composable
fun MmasNavigation(
    homeScreenViewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MmasScreen.HomeScreen.route
    ) {

        composable(MmasScreen.HomeScreen.route) {
            HomeScreen(homeScreenViewModel = homeScreenViewModel, navController = navController)
        }

        composable(MmasScreen.InputScreen.route) {
            InputScreen()
        }

        composable(MmasScreen.AnalysisScreen.route) {
            AnalysisScreen()
        }

        composable(MmasScreen.CalendarScreen.route) {
            CalendarScreen()
        }

        composable(MmasScreen.SettingScreen.route) {
            SettingScreen()
        }

        composable(MmasScreen.TransactionScreen.route) {
            TransactionScreen(
                navController = navController
            )
        }

        composable(
            route = MmasScreen.SearchScreen.route
        ) {
            SearchScreen(
                bundle = it.arguments,
                navController = navController
            )
        }
    }
}

