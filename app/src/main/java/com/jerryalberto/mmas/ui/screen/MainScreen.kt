package com.jerryalberto.mmas.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import com.jerryalberto.mmas.feature.home.ui.viewmodel.HomeScreenViewModel
import com.jerryalberto.mmas.ui.navigation.MainNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
    bottomBar: @Composable () -> Unit = {}
) {
    Scaffold (
        //containerColor = Color.Transparent,
        bottomBar = bottomBar
    ) { paddingValues ->

        MainNavigation(
            homeScreenViewModel = homeScreenViewModel,
            modifier = Modifier.padding(paddingValues),
            navController = navController
        )
    }
}

