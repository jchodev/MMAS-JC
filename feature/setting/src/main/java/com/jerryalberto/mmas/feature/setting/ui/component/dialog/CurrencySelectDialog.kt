package com.jerryalberto.mmas.feature.setting.ui.component.dialog


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jerryalberto.mmas.core.model.data.CountryData
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource

import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens

import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.feature.setting.R
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CurrencySelectDialog(
    modifier: Modifier = Modifier,
    countryList: List<CountryData> = listOf(),
    defaultCountryCode: String,
    onDismissRequest: () -> Unit = {},
    onSelected: (item: CountryData) -> Unit = {},
){

    var searchValue by remember { mutableStateOf("") }
    var filteredItems by remember { mutableStateOf(countryList) }

    BaseSelectDialog(
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
        leadingContent = {
            Text (
                text = it.countryFlag,
                style = MaterialTheme.typography.titleMedium
            )
        },
        headlineContent = {
            Column{
                Text(
                    text =  "${it.currency.currencyCode} ( ${it.currency.displayName})",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    //stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                    text =   "${it.countryName} ( ${it.countryNameEng})",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        },
        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    //stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                    text = it.currency.symbol,
                    style = MaterialTheme.typography.titleMedium,
                )
                if (it.countryCode == defaultCountryCode){
                    Icon(
                        modifier = Modifier.padding(MaterialTheme.dimens.dimen8),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_selected),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                    )
                }
            }
        },
        onItemSelected = onSelected
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
            },
            defaultCountryCode = "AED"
        )
    }
}