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

import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme

import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.feature.setting.R
import java.text.NumberFormat
import java.util.Locale

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


@DevicePreviews
@Composable
private fun CurrencySelectDialogPreview(){
    MmasTheme {
        CurrencySelectDialog(
            countryList = Locale.getISOCountries().map {
                CountryData(
                    countryCode = it,
                    countryName = "countryName",
                    countryNameEng = "countryNameEng",
                    countryFlag = "f",
                    currency = NumberFormat.getCurrencyInstance(Locale("", it)).currency
                )
            }
        )
    }
}