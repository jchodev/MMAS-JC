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
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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

    private val bottomBar = @Composable {
        BottomBar(
            items = listOf(
                BottomBarItem(
                    title = "Home",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    onClick = {
                        navController?.navigate(MmasScreen.HomeScreen.route)
                    }
                ),
                BottomBarItem(
                    title = "Transaction",
                    selectedIcon = Icons.Filled.Analytics,
                    unselectedIcon = Icons.Outlined.Analytics,
                    onClick = {
                        navController?.navigate(MmasScreen.TransactionScreen.route)
                    }
                ),
                BottomBarItem(
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

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    navController?.let {
                        MainScreen(
                            navController = it,
                            bottomBar = bottomBar
                        )
                    }
                }
            }
        }
    }
}
