package com.jerryalberto.mmas.feature.transaction.ui.screen

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jerryalberto.mmas.core.designsystem.textfield.TopBarSearchTextField
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.ui.constants.BundleParamKey
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.feature.transaction.R
import com.jerryalberto.mmas.feature.transaction.component.TransactionsList
import com.jerryalberto.mmas.feature.transaction.model.TransactionData

@Composable
fun SearchScreen(
    appNavController: NavHostController,
    setting: Setting,
    bundle: Bundle?,
) {
    SearchScreenContent(
        onTopBarLeftClick = {
            appNavController.popBackStack()
        },
        transactionList = bundle?.getParcelableArrayList(BundleParamKey.PARAM_LIST) ?: emptyList(),
        setting = setting,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    setting: Setting = Setting(),
    onTopBarLeftClick : () -> Unit = {},
    transactionList: (List<TransactionData>) = emptyList()
) {

    var searchValue by remember { mutableStateOf("") }
    var isSearch by remember { mutableStateOf(false) }
    var filteredItems by remember { mutableStateOf(transactionList) }

    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(isSearch) {
                if (isSearch) {
                    focusRequester.requestFocus()
                }
            }
            TopAppBar(
                modifier = Modifier.shadow(elevation = MaterialTheme.dimens.dimen4),
                title = {
                    TopBarSearchTextField(
                        searchValue = searchValue,
                        onValueChange = { searchStr ->
                            searchValue = searchStr
                            filteredItems = transactionList.searchForAnItem(searchStr)
                        },
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onTopBarLeftClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = stringResource(id = com.jerryalberto.mmas.core.designsystem.R.string.close),
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isSearch = false
                        searchValue = ""
                        filteredItems = transactionList.searchForAnItem(searchValue)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.feature_transaction_clear),
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column (modifier = Modifier
            .padding(paddingValues)
            .padding(MaterialTheme.dimens.dimen16)){
            TransactionsList(
                transactionData = if (searchValue.isEmpty()) {
                    transactionList
                } else {
                    filteredItems
                },
                setting = setting,
            )
        }
    }
}

private fun List<TransactionData>.searchForAnItem(
    searchStr: String,
): List<TransactionData> {
    val output: MutableList<TransactionData> = mutableListOf()
    forEach {
        val filtered = it.transactions.filter { transaction ->
            transaction.description.contains(searchStr, ignoreCase = true) ||
                    transaction.category?.type?.value?.contains(searchStr, ignoreCase = true) == true
        }
        if (filtered.isNotEmpty()){
            output.add(
                TransactionData(
                    date = it.date,
                    transactions = filtered,
                    totalAmount = filtered.sumOf { it.amount }
                )
            )
        }
    }

    return output
}

@DevicePreviews
@Composable
private fun TransactionScreenContentPreview() {
    MmasTheme {
        SearchScreenContent(
            transactionList = mockTransactionList,
        )
    }
}