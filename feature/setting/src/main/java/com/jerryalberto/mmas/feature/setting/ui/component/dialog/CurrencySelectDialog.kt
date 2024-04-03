package com.jerryalberto.mmas.feature.setting.ui.component.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.jerryalberto.mmas.core.model.data.CountryData
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.ui.res.stringResource
import com.jerryalberto.mmas.core.designsystem.textfield.TopBarSearchTextField
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.domain.usecase.SettingUseCase
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.feature.setting.R

@Composable
fun CurrencySelectDialog(
    modifier: Modifier = Modifier,
    countryList: List<CountryData> = listOf(),
    onDismissRequest: () -> Unit = {},
    onSelected: (item: CountryData) -> Unit = {},
){
    val itemContent = @Composable { countryItem: CountryData  -> // Specify type and default
        ListItem(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onSelected(countryItem)
                },
            leadingContent = {
                Text (
                    text = countryItem.countryFlag,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            headlineContent = {
                Column{
                    Text(
                        text =  "${countryItem.currency.currencyCode} ( ${countryItem.currency.displayName})",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        //stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                        text =   "${countryItem.countryName} ( ${countryItem.countryNameEng})",
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            },
            trailingContent = {
                Text(
                    //stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                    text =  countryItem.currency.symbol,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        )
    }

    var searchValue by remember { mutableStateOf("") }
    var filteredItems by remember { mutableStateOf(countryList) }

    SelectDialog(
        modifier = modifier,
        fullList = countryList,
        filteredItems = filteredItems,
        searchValue = searchValue,
        onDismissRequest = onDismissRequest,
        onValueChange = { searchStr ->
            searchValue = searchStr
            filteredItems = countryList.searchForAnItem(searchStr)
        },
        title = stringResource(id = R.string.feature_setting_select_currency),
        itemContent = {
            itemContent(it)
        }
    )
}

private fun List<CountryData>.searchForAnItem(
    searchStr: String,
): List<CountryData> {
    val filteredItems = filter {
        it.countryName.contains(
            searchStr,
            ignoreCase = true,
        ) ||
        it.countryNameEng.contains(
            searchStr,
            ignoreCase = true,
        ) ||
        it.countryCode.contains(
            searchStr,
            ignoreCase = true,
        )
    }

    return filteredItems.toList()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelectDialog2(
    modifier: Modifier = Modifier,
    countryList: List<CountryData> = listOf(),
    onDismissRequest: () -> Unit = {},
    onSelected: (item: CountryData) -> Unit = {},
    properties: DialogProperties = DialogProperties().let {
        DialogProperties(
            dismissOnBackPress = it.dismissOnBackPress,
            dismissOnClickOutside = it.dismissOnClickOutside,
            securePolicy = it.securePolicy,
            usePlatformDefaultWidth = false,
        )
    },
) {
    var searchValue by remember { mutableStateOf("") }
    var isSearch by remember { mutableStateOf(false) }
    var filteredItems by remember { mutableStateOf(countryList) }

    BasicAlertDialog(
        modifier = modifier.fillMaxSize(),
        onDismissRequest = onDismissRequest,
        properties = properties,
        content = {
            Surface(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                if (isSearch) {
                                    TopBarSearchTextField(
                                        searchValue = searchValue,
                                        onValueChange = { searchStr ->
                                            searchValue = searchStr
                                            filteredItems = countryList.searchForAnItem(searchStr)
                                        }
                                    )
                                } else {
                                    Text(
                                        text = stringResource(id = R.string.feature_setting_select_currency),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    onDismissRequest()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = null,
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = {
                                    isSearch = !isSearch
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                    )
                                }
                            },
                        )
                    },
                ) { paddingValues ->
                    LazyColumn(
                        Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                    ) {
                        val countriesData =
                            if (searchValue.isEmpty()) {
                                countryList
                            } else {
                                filteredItems
                            }
                        items(countriesData) { countryItem->
                            ListItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSelected(countryItem)
                                    },
                                leadingContent = {
                                    Text (
                                        text = countryItem.countryFlag,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                },
                                headlineContent = {
                                    Column{
                                        Text(
                                            text =  "${countryItem.currency.currencyCode} ( ${countryItem.currency.displayName})",
                                            style = MaterialTheme.typography.titleMedium,
                                        )
                                        Text(
                                            //stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                                            text =   "${countryItem.countryName} ( ${countryItem.countryNameEng})",
                                            style = MaterialTheme.typography.titleSmall,
                                        )
                                    }
                                },
                                trailingContent = {
                                    Text(
                                        //stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                                        text =  countryItem.currency.symbol,
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}


@DevicePreviews
@Composable
private fun CurrencySelectDialogPreview(){
    MmasTheme {
        CurrencySelectDialog(
            countryList = SettingUseCase().getCountryList()
        )
    }
}