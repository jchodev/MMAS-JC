package com.jerryalberto.mmas.feature.setting.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.designsystem.topbar.MmaTopBar
import com.jerryalberto.mmas.core.domain.usecase.SettingUseCase
import com.jerryalberto.mmas.core.model.data.CountryData
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.core.ui.screen.MmasScreen
import com.jerryalberto.mmas.feature.setting.R
import com.jerryalberto.mmas.feature.setting.ui.component.SettingItem
import com.jerryalberto.mmas.feature.setting.ui.component.dialog.CurrencySelectDialog
import com.jerryalberto.mmas.feature.setting.ui.uistate.SettingUIDataState
import com.jerryalberto.mmas.feature.setting.ui.viewmodel.SettingViewModel
import timber.log.Timber

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    SettingScreenContent(
        uiState = viewModel.uiState.collectAsState().value,
        countryList = viewModel.getCountryList(),
        onCountrySelected = {
            viewModel.onCountryDataSelected(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingScreenContent(
    uiState: SettingUIDataState = SettingUIDataState(),
    countryList: List<CountryData> = emptyList(),
    onCountrySelected: (CountryData) -> Unit = {},
) {
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MmaTopBar(
                modifier = Modifier.shadow(elevation = MaterialTheme.dimens.dimen4),
                title = stringResource(id = R.string.feature_setting_setting),
                showBack = false
            )
        }
    ){
        paddingValues ->

        //Nationality
        var openCountrySelectDialog by remember { mutableStateOf(false) }
        if (openCountrySelectDialog) {
            CurrencySelectDialog(
                countryList = countryList,
                onDismissRequest = { openCountrySelectDialog = false },
                onSelected = {
                    openCountrySelectDialog = false
                    Timber.d("selected Country is ${it}")
                    onCountrySelected.invoke(it)
                },

            )
        }

        LazyColumn (modifier = Modifier
            .padding(paddingValues) ){
            //Currency
            item {
                SettingItem(
                    title = "Currency",
                    selectedValue = uiState.countryData?.currency?.symbol ?: "$",
                    onClick = {
                        openCountrySelectDialog = true
                    }
                )
            }
            //Language
            item {
                SettingItem(
                    title = "Language",
                    selectedValue = "English"
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun SettingScreenContentPreview(){
    MmasTheme {
        SettingScreenContent(
            uiState = SettingUIDataState(
                countryData = SettingUseCase().getDefaultCountry()
            )
        )
    }
}