package com.jerryalberto.mmas.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.jerryalberto.mmas.feature.analysis.ui.screen.AnalysisScreen
import com.jerryalberto.mmas.feature.calendar.ui.screen.CalendarScreen
import com.jerryalberto.mmas.feature.home.ui.screen.InputScreen
import com.jerryalberto.mmas.feature.setting.ui.screen.SettingScreen
import com.jerryalberto.mmas.feature.home.ui.screen.HomeScreen
import com.jerryalberto.mmas.feature.home.ui.viewmodel.HomeScreenViewModel
import com.jerryalberto.mmas.feature.transaction.ui.screen.TransactionScreen
import com.jerryalberto.mmas.core.ui.screen.MmasScreen
import com.jerryalberto.mmas.feature.setting.ui.viewmodel.SettingViewModel

import com.jerryalberto.mmas.feature.transaction.ui.screen.SearchScreen

@Composable
fun MmasNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    val settingViewModel: SettingViewModel = hiltViewModel()
    val setting = settingViewModel.settingState.collectAsState().value

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MmasScreen.HomeScreen.route
    ) {

        composable(MmasScreen.HomeScreen.route) {
            HomeScreen(navController = navController, setting = setting)
        }

        composable(MmasScreen.InputScreen.route) {
            InputScreen(
                navController = navController,
                bundle = it.arguments,
                setting = setting,
            )
        }

        composable(MmasScreen.AnalysisScreen.route) {
            AnalysisScreen()
        }

        composable(MmasScreen.CalendarScreen.route) {
            CalendarScreen()
        }

        composable(MmasScreen.SettingScreen.route) {
            SettingScreen(
                viewModel = settingViewModel
            )
        }

//        composable(MmasScreen.SettingScreen.route) {entry ->
//           val viewModel = entry.sharedViewModel<SettingViewModel>(navController)
//           val viewModel2 = entry.sharedViewModel2<SharedSettingViewModel>(navController)
//           Text(
//              "this is new setting"
//           )
//        }

        composable(MmasScreen.TransactionScreen.route) {
            TransactionScreen(
                settingViewModel = settingViewModel,
                navController = navController,
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

//https://stackoverflow.com/questions/68548488/sharing-viewmodel-within-jetpack-compose-navigation
//@Composable
//inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
//    navController: NavHostController,
//): T {
//    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
//    val parentEntry = remember(this) {
//        navController.getBackStackEntry(navGraphRoute)
//    }
//    return hiltViewModel(parentEntry)
//}
//
//@Composable
//inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel2(
//    navController: NavHostController,
//): T {
//    val navGraphRoute = destination.parent?.route ?: return viewModel()
//    val parentEntry = remember(this) {
//        navController.getBackStackEntry(navGraphRoute)
//    }
//    return viewModel(parentEntry)
//}
