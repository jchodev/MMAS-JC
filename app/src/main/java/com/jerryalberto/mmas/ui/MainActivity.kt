package com.jerryalberto.mmas.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.ui.screen.MmasScreen
import com.jerryalberto.mmas.ui.components.BottomBar
import com.jerryalberto.mmas.ui.components.BottomBarItem
import com.jerryalberto.mmas.ui.screen.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var navController: NavHostController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //test get currency
        val isoCountryCodes: Array<String> = Locale.getISOCountries()
        for (countryCode in isoCountryCodes) {
            val locale = Locale("", countryCode)
            val countryName = locale.getDisplayCountry(Locale.getDefault())
            val numberFormat = NumberFormat.getCurrencyInstance(locale)
            val currency = numberFormat.currency
            val symbol = currency?.symbol
            Timber.d("numberFormat.currency${numberFormat.currency} symbol: ${symbol} : countryName is ${countryName}")
        }

        setContent {
            MmasTheme {
                navController = rememberNavController()
                val currentRoute by navController!!.currentRouteAsState()
                val currentSelectedScreen by navController!!.currentScreenAsState()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    navController?.let {
                        MainScreen(
                            navController = it,
                            bottomBar = {
                                BottomBar(
                                    currentSelectedScreen = currentSelectedScreen,
                                    items = listOf(
                                        BottomBarItem(
                                            route = MmasScreen.HomeScreen.route,
                                            title = "Home",
                                            selectedIcon = Icons.Filled.Home,
                                            unselectedIcon = Icons.Outlined.Home,
                                            onClick = {
                                                navController?.navigate(MmasScreen.HomeScreen.route)
                                            }
                                        ),
                                        BottomBarItem(
                                            route = MmasScreen.TransactionScreen.route,
                                            title = "Transaction",
                                            selectedIcon = Icons.Filled.Analytics,
                                            unselectedIcon = Icons.Outlined.Analytics,
                                            onClick = {
                                                navController?.navigate(MmasScreen.TransactionScreen.route)
                                            }
                                        ),
                                        BottomBarItem(
                                            route = MmasScreen.SettingScreen.route,
                                            title = "Setting",
                                            selectedIcon = Icons.Filled.Settings,
                                            unselectedIcon = Icons.Outlined.Settings,
                                            onClick = {
                                                navController?.navigate(MmasScreen.SettingScreen.route)
                                            }
                                        )
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Stable
@Composable
private fun NavController.currentScreenAsState(): State<MmasScreen> {
    val selectedItem = remember { mutableStateOf<MmasScreen>(MmasScreen.HomeScreen) }
    DisposableEffect(key1 = this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == MmasScreen.HomeScreen.route } -> {
                    selectedItem.value = MmasScreen.HomeScreen
                }
                destination.hierarchy.any { it.route == MmasScreen.TransactionScreen.route } -> {
                    selectedItem.value = MmasScreen.TransactionScreen
                }
                destination.hierarchy.any { it.route == MmasScreen.SettingScreen.route } -> {
                    selectedItem.value = MmasScreen.SettingScreen
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
