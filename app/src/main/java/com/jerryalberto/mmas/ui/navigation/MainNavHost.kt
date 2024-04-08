package com.jerryalberto.mmas.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jerryalberto.mmas.core.ui.navigation.MainRoute
import com.jerryalberto.mmas.feature.home.ui.screen.HomeScreen
import com.jerryalberto.mmas.feature.home.ui.viewmodel.HomeScreenViewModel

import com.jerryalberto.mmas.feature.setting.ui.screen.SettingScreen
import com.jerryalberto.mmas.feature.setting.ui.viewmodel.SettingViewModel
import com.jerryalberto.mmas.feature.transaction.ui.screen.TransactionScreen
import com.jerryalberto.mmas.ui.components.BottomBar
import com.jerryalberto.mmas.ui.components.BottomBarItem


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavHost(
    appNavController: NavHostController = rememberNavController(),
    settingViewModel: SettingViewModel,
) {

    val mainNavController = rememberNavController()
    val currentSelectedScreen by mainNavController.currentScreenAsState()

    val setting = settingViewModel.settingState.collectAsState().value

    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

    Scaffold(
        bottomBar = {
            BottomBar(
                currentSelectedScreen = currentSelectedScreen,
                items = listOf(
                    BottomBarItem(
                        route = MainRoute.HomeScreen.route,
                        title = "Home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        onClick = {
                            mainNavController.navigate(MainRoute.HomeScreen.route)
                        }
                    ),
                    BottomBarItem(
                        route = MainRoute.TransactionScreen.route,
                        title = "Transaction",
                        selectedIcon = Icons.Filled.Analytics,
                        unselectedIcon = Icons.Outlined.Analytics,
                        onClick = {
                            mainNavController.navigate(MainRoute.TransactionScreen.route)
                        }
                    ),
                    BottomBarItem(
                        route = MainRoute.SettingScreen.route,
                        title = "Setting",
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                        onClick = {
                            mainNavController.navigate(MainRoute.SettingScreen.route)
                        }
                    )
                )
            )
        }
    ) {
        NavHost(
            modifier = Modifier.fillMaxSize().padding(it),
            navController = mainNavController,
            startDestination = MainRoute.HomeScreen.route
        ){
            composable(MainRoute.HomeScreen.route) {
                HomeScreen(
                    mainNavController = mainNavController,
                    appNavController = appNavController,
                    setting = setting,
                    homeScreenViewModel = homeScreenViewModel,
                )
            }

            composable(MainRoute.TransactionScreen.route) {
                TransactionScreen(
                    setting = setting,
                    appNavController = appNavController,
                )
            }

            composable(MainRoute.SettingScreen.route) {
                SettingScreen(
                    viewModel = settingViewModel
                )
            }
        }
    }
}

@Stable
@Composable
private fun NavController.currentScreenAsState(): State<MainRoute> {
    val selectedItem = remember { mutableStateOf<MainRoute>(MainRoute.HomeScreen) }
    DisposableEffect(key1 = this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route ==MainRoute.HomeScreen.route } -> {
                    selectedItem.value = MainRoute.HomeScreen
                }
                destination.hierarchy.any { it.route == MainRoute.TransactionScreen.route } -> {
                    selectedItem.value = MainRoute.TransactionScreen
                }
                destination.hierarchy.any { it.route == MainRoute.SettingScreen.route } -> {
                    selectedItem.value = MainRoute.SettingScreen
                }
            }

        }
        addOnDestinationChangedListener(listener)
        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }
    return selectedItem
}

@Stable
@Composable
private fun NavController.currentRouteAsState(): State<String?> {
    val selectedItem = remember { mutableStateOf<String?>(null) }
    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            selectedItem.value = destination.route
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }
    return selectedItem
}
