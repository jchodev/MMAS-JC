package com.jerryalberto.mmas.feature.transaction.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BasicAlertDialog
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
import androidx.compose.ui.window.DialogProperties
import com.jerryalberto.mmas.core.designsystem.R
import com.jerryalberto.mmas.core.designsystem.textfield.TopBarSearchTextField
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.feature.transaction.component.TransactionsList
import com.jerryalberto.mmas.feature.transaction.model.TransactionGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionSearchDialog(
    modifier: Modifier = Modifier,
    setting: Setting,
    transactionGroupList: (List<TransactionGroup>) = emptyList(),
    onDismissRequest: () -> Unit = {},
    properties: DialogProperties = DialogProperties().let {
        DialogProperties(
            dismissOnBackPress = it.dismissOnBackPress,
            dismissOnClickOutside = it.dismissOnClickOutside,
            securePolicy = it.securePolicy,
            usePlatformDefaultWidth = false,
        )
    },
) {

    BasicAlertDialog(
        modifier = modifier.fillMaxSize(),
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        TransactionSearchContent(
            setting = setting,
            transactionGroupList = transactionGroupList,
            onDismissRequest = onDismissRequest
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionSearchContent(
    setting: Setting,
    transactionGroupList: (List<TransactionGroup>) = emptyList(),
    onDismissRequest: () -> Unit = {},
){
    var searchValue by remember { mutableStateOf("") }
    var isSearch by remember { mutableStateOf(false) }
    var filteredItems by remember { mutableStateOf(transactionGroupList) }

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
                            filteredItems = transactionGroupList.searchForAnItem(searchStr)
                        },
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = stringResource(id = R.string.core_designsystem_close),
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isSearch = false
                        searchValue = ""
                        filteredItems = transactionGroupList.searchForAnItem(searchValue)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(id = com.jerryalberto.mmas.feature.transaction.R.string.feature_transaction_clear),
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
                    transactionGroupList
                } else {
                    filteredItems
                },
                setting = setting,
            )
        }
    }
}

private fun List<TransactionGroup>.searchForAnItem(
    searchStr: String,
): List<TransactionGroup> {
    val output: MutableList<TransactionGroup> = mutableListOf()
    forEach {
        val filtered = it.transactions.filter { transaction ->
            transaction.description.contains(searchStr, ignoreCase = true) ||
                    transaction.category?.type?.value?.contains(searchStr, ignoreCase = true) == true
        }
        if (filtered.isNotEmpty()){
            output.add(
                TransactionGroup(
                    date = it.date,
                    transactions = filtered,
                    totalAmount = filtered.sumOf { it.amount }
                )
            )
        }
    }

    return output
}
