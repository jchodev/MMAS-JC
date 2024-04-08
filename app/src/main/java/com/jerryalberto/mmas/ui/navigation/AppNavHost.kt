package com.jerryalberto.mmas.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jerryalberto.mmas.core.ui.navigation.AppRoute
import com.jerryalberto.mmas.feature.home.ui.screen.InputScreen
import com.jerryalberto.mmas.feature.setting.ui.viewmodel.SettingViewModel
import com.jerryalberto.mmas.feature.transaction.ui.screen.SearchScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val settingViewModel: SettingViewModel = hiltViewModel()
    val setting = settingViewModel.settingState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = AppRoute.MainNavHost.route
    ) {
        composable(route = AppRoute.MainNavHost.route) {
            MainNavHost(
                appNavController = navController,
                settingViewModel = settingViewModel
            )
        }
        composable(route = AppRoute.InputScreen.route) {
            InputScreen(
                appNavController = navController,
                bundle = it.arguments,
                setting = setting,
            )
        }
        composable(route = AppRoute.SearchScreen.route) {
            SearchScreen(
                appNavController = navController,
                bundle = it.arguments,
                setting = setting,
            )
        }
    }

}