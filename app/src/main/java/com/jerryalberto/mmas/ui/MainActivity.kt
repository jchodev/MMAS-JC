package com.jerryalberto.mmas.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.feature.home.ui.viewmodel.HomeScreenViewModel
import com.jerryalberto.mmas.feature.transaction.ui.viewmodel.TransactionViewModel
import com.jerryalberto.mmas.ui.components.BottomBar
import com.jerryalberto.mmas.ui.components.BottomBarItem
import com.jerryalberto.mmas.ui.navigation.MainActivityScreen
import com.jerryalberto.mmas.ui.screen.MainScreen
import dagger.hilt.android.AndroidEntryPoint

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
                        navController?.navigate(MainActivityScreen.HomeScreen.route)
                    }
                ),
                BottomBarItem(
                    title = "Transaction",
                    selectedIcon = Icons.Filled.Analytics,
                    unselectedIcon = Icons.Outlined.Analytics,
                    onClick = {
                        navController?.navigate(MainActivityScreen.TransactionScreen.route)
                    }
                ),
                BottomBarItem(
                    title = "Setting",
                    selectedIcon = Icons.Filled.Settings,
                    unselectedIcon = Icons.Outlined.Settings,
                    onClick = {
                        navController?.navigate(MainActivityScreen.SettingScreen.route)
                    }
                )
            )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
