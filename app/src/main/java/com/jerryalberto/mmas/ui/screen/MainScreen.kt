package com.jerryalberto.mmas.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavHostController
import com.jerryalberto.mmas.feature.setting.ui.viewmodel.SettingViewModel
import com.jerryalberto.mmas.ui.navigation.MmasNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    settingViewModel: SettingViewModel = hiltViewModel(),
    navController: NavHostController,
    bottomBar: @Composable () -> Unit = {}
) {
    Scaffold (
        //containerColor = Color.Transparent,
        bottomBar = bottomBar
    ) { paddingValues ->

        MmasNavigation(
            settingViewModel = settingViewModel,
            modifier = Modifier.padding(paddingValues),
            navController = navController
        )
    }
}

