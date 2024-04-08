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
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.designsystem.topbar.MmaTopBar
import com.jerryalberto.mmas.core.model.data.CountryData
import com.jerryalberto.mmas.core.model.data.TimeFormatType
import com.jerryalberto.mmas.core.ui.ext.getString
import com.jerryalberto.mmas.core.ui.ext.toCountryData
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.feature.setting.R
import com.jerryalberto.mmas.feature.setting.ui.component.SettingItem
import com.jerryalberto.mmas.feature.setting.ui.component.dialog.CurrencySelectDialog
import com.jerryalberto.mmas.feature.setting.ui.component.dialog.DateFormatDialog
import com.jerryalberto.mmas.feature.setting.ui.component.dialog.TimeFormatDialog
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
            viewModel.onCountryDataSelected(it.countryCode)
        },
        dateFormatList = viewModel.getDateFormatList(),
        onDateFormatSelected = {
            viewModel.onDateFormatSelected(it)
        },
        timeFormatList = viewModel.getTimeFormatList(),
        onTimeFormatSelected = {
            viewModel.onTimeFormatSelected(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingScreenContent(
    uiState: SettingUIDataState = SettingUIDataState(),
    countryList: List<CountryData> = emptyList(),
    dateFormatList: List<String> = emptyList(),
    timeFormatList: List<TimeFormatType> = emptyList(),
    onCountrySelected: (CountryData) -> Unit = {},
    onDateFormatSelected: (String) -> Unit = {},
    onTimeFormatSelected: (TimeFormatType) -> Unit = {},
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

        //Currency
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
                defaultCountryCode = uiState.setting.countryCode,
            )
        }

        //date format
        var openDateFormatSelectDialog by remember { mutableStateOf(false) }
        if (openDateFormatSelectDialog) {
            DateFormatDialog(
                itemList = dateFormatList,
                onDismissRequest = { openDateFormatSelectDialog = false },
                onItemSelected = {
                    openDateFormatSelectDialog = false
                    Timber.d("selected data format is ${it}")
                    onDateFormatSelected.invoke(it)
                },
                defaultItem = uiState.setting.dateFormat,
            )
        }

        //time format
        var openTimeFormatSelectDialog by remember { mutableStateOf(false) }
        if (openTimeFormatSelectDialog) {
            TimeFormatDialog(
                itemList = timeFormatList,
                onDismissRequest = { openTimeFormatSelectDialog = false },
                onItemSelected = {
                    openTimeFormatSelectDialog = false
                    Timber.d("selected time format is ${it}")
                    onTimeFormatSelected.invoke(it)
                },
                defaultItem = uiState.setting.timeFormatType
            )
        }

        //theme
//        var openThemeSelectDialog by remember { mutableStateOf(false) }
//        if (openThemeSelectDialog) {
//            ThemeDialog(
//                dateFormatList = listOf(
//                    "Default",
//                    "Light",
//                    "Dark"
//                ),
//                onDismissRequest = { openThemeSelectDialog = false },
//                onSelected = {
//                    openThemeSelectDialog = false
//                    Timber.d("selected theme is ${it}")
//                    onDateFormatSelected.invoke(it)
//                },
//            )
//        }


        LazyColumn (modifier = Modifier
            .padding(paddingValues) ){
            //Currency
            item {
                SettingItem(
                    title = "Currency",
                    selectedValue = uiState.setting.countryCode.toCountryData().currency.symbol,
                    onClick = {
                        openCountrySelectDialog = true
                    }
                )
            }
            //Date format
            item {
                SettingItem(
                    title = "Data Format",
                    selectedValue = uiState.setting.dateFormat,
                    onClick = {
                        openDateFormatSelectDialog = true
                    }
                )
            }
            //Time format
            item {
                SettingItem(
                    title = "Time Format",
                    selectedValue = uiState.setting.timeFormatType.getString(),
                    onClick = {
                        openTimeFormatSelectDialog = true
                    }
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun SettingScreenContentPreview(){
    MmasTheme {
        SettingScreenContent()
    }
}