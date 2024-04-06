package com.jerryalberto.mmas.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jerryalberto.mmas.core.ui.navigation.AppRoute
import com.jerryalberto.mmas.feature.home.ui.screen.InputScreen
import com.jerryalberto.mmas.feature.setting.ui.viewmodel.SettingViewModel
import com.jerryalberto.mmas.feature.transaction.ui.screen.SearchScreen
import com.jerryalberto.mmas.ui.screen.MainScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val settingViewModel: SettingViewModel = hiltViewModel()
    val setting = settingViewModel.settingState.collectAsState().value

    Scaffold {
        Box(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = AppRoute.MainScreen.route
            ) {
                composable(route = AppRoute.MainScreen.route) {
                    MainScreen(
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
    }
}