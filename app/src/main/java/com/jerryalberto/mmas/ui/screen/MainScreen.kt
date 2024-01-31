package com.jerryalberto.mmas.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import com.jerryalberto.mmas.ui.navigation.MainNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    bottomBar: @Composable () -> Unit = {},
) {
    Scaffold (
        //containerColor = Color.Transparent,
        bottomBar = bottomBar
    ) { paddingValues ->

        MainNavigation(
            navController = navController
        )
    }
}

