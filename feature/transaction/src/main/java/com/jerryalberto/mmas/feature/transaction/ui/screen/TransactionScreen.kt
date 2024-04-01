package com.jerryalberto.mmas.feature.transaction.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.designsystem.topbar.MmaTopBar
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.core.ui.component.SpendFrequencyButton
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.feature.transaction.component.TransactionsList
import com.jerryalberto.mmas.feature.transaction.model.TransactionData
import com.jerryalberto.mmas.feature.transaction.ui.model.YearMonthItem
import com.jerryalberto.mmas.feature.transaction.ui.uistate.TransactionUIDataState
import com.jerryalberto.mmas.feature.transaction.ui.viewmodel.TransactionViewModel
import java.util.Calendar
import androidx.hilt.navigation.compose.hiltViewModel
import com.jerryalberto.mmas.feature.transaction.R

@Composable
fun TransactionScreen(
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    TransactionScreenContent(
        uiState = uiState,
        onYearMonthItemClick = {
            viewModel.getTransactionsByYearMonth(year = it.year, month = it.month)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionScreenContent(
    uiState: TransactionUIDataState = TransactionUIDataState(),
    onYearMonthItemClick : (YearMonthItem) -> Unit = {},
){

    var searchValue by remember { mutableStateOf("") }
    var isSearch by remember { mutableStateOf(false) }
    var filteredItems by remember { mutableStateOf(uiState.transactionList) }

    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
//            MmaTopBar(
//                modifier = Modifier
//                    .shadow(elevation = MaterialTheme.dimens.dimen4),
//                title = "this is title",
//                //onCloseClick = onTopBarLeftClick
//            )
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(isSearch) {
                if (isSearch) {
                    focusRequester.requestFocus()
                }
            }
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(elevation = MaterialTheme.dimens.dimen4),
                title = {
                    if (isSearch) {
                        TextField(
                            modifier = Modifier.focusRequester(focusRequester),
                            value = searchValue,
                            onValueChange = { searchStr ->
                                searchValue = searchStr
                                filteredItems = uiState.transactionList.searchForAnItem(searchStr)
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.feature_transaction_search),
                                    color = MaterialTheme.colorScheme.outline,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            textStyle = MaterialTheme.typography.labelLarge,
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.feature_transaction_title),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                navigationIcon = {
                    if (isSearch){
                        IconButton(onClick = {

                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.feature_transaction_search),
                            )
                        }
                    }
                },
                actions = {
                    if (isSearch){
                        IconButton(onClick = {
                            isSearch = false
                            searchValue = ""
                            filteredItems = uiState.transactionList.searchForAnItem(searchValue)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(id = R.string.feature_transaction_clear),
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            isSearch = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.feature_transaction_search),
                            )
                        }
                    }

                },
            )
        }
    ) { paddingValues ->

        //data select lazyRow
        Column (modifier = Modifier
            .padding(paddingValues)
            .padding(MaterialTheme.dimens.dimen16)){
            LazyRow {
                uiState.listOfYearMonth.forEach {
                    item {
                        SpendFrequencyButton(
                            modifier = Modifier.wrapContentSize(),
                            text = it.month.toString() + " " + it.year.toString(),
                            selected = it.selected,
                            onClick = {
                                onYearMonthItemClick.invoke(it)
                            }
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))

            TransactionsList(
                transactionData = if (searchValue.isEmpty()) {
                    uiState.transactionList
                } else {
                    filteredItems
                }
            )
        }

    }
}


@DevicePreviews
@Composable
private fun TransactionScreenContentPreview() {

    MmasTheme {
        TransactionScreenContent(
            uiState = TransactionUIDataState(
                listOfYearMonth = listOf(
                    YearMonthItem(
                        year = 2024,
                        month = 1,
                        selected  = false,
                    ),
                    YearMonthItem(
                        year = 2024,
                        month = 2,
                        selected  = true,
                    )
                ),
                transactionList = listOf(
                    TransactionData(
                        date = Calendar.getInstance().timeInMillis,
                        totalAmount = 1.0,
                        transactions = listOf(
                            Transaction(
                                category = Category(type = CategoryType.GIFTS),
                                id = 1,
                                type = TransactionType.EXPENSES,
                                amount = 1.0,
                                description = "description",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 1,
                                minute = 1,
                                uri = ""
                            ),
                            Transaction(
                                category = Category(type = CategoryType.SALARY),
                                id = 2,
                                type = TransactionType.INCOME,
                                amount = 2.0,
                                description = "description2",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 2,
                                minute = 1,
                                uri = ""
                            )
                        )
                    ),
                    TransactionData(
                        date = Calendar.getInstance().timeInMillis,
                        totalAmount = -2.0,
                        transactions = listOf(
                            Transaction(
                                category = Category(type = CategoryType.GIFTS),
                                id = 1,
                                type = TransactionType.EXPENSES,
                                amount = 1.0,
                                description = "description",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 1,
                                minute = 1,
                                uri = ""
                            ),
                            Transaction(
                                category = Category(type = CategoryType.SALARY),
                                id = 2,
                                type = TransactionType.INCOME,
                                amount = 2.0,
                                description = "description2",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 2,
                                minute = 1,
                                uri = ""
                            )
                        )
                    ),
                    TransactionData(
                        date = Calendar.getInstance().timeInMillis,
                        totalAmount = 3.0,
                        transactions = listOf(
                            Transaction(
                                category = Category(type = CategoryType.GIFTS),
                                id = 1,
                                type = TransactionType.EXPENSES,
                                amount = 1.0,
                                description = "description",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 1,
                                minute = 1,
                                uri = ""
                            ),
                            Transaction(
                                category = Category(type = CategoryType.SALARY),
                                id = 2,
                                type = TransactionType.INCOME,
                                amount = 2.0,
                                description = "description2",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 2,
                                minute = 1,
                                uri = ""
                            )
                        )
                    ),
                    TransactionData(
                        date = Calendar.getInstance().timeInMillis,
                        totalAmount = 4.0,
                        transactions = listOf(
                            Transaction(
                                category = Category(type = CategoryType.GIFTS),
                                id = 1,
                                type = TransactionType.EXPENSES,
                                amount = 1.0,
                                description = "description",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 1,
                                minute = 1,
                                uri = ""
                            ),
                            Transaction(
                                category = Category(type = CategoryType.SALARY),
                                id = 2,
                                type = TransactionType.INCOME,
                                amount = 2.0,
                                description = "description2",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 2,
                                minute = 1,
                                uri = ""
                            )
                        )
                    ),
                    TransactionData(
                        date = Calendar.getInstance().timeInMillis,
                        totalAmount = 5.0,
                        transactions = listOf(
                            Transaction(
                                category = Category(type = CategoryType.GIFTS),
                                id = 1,
                                type = TransactionType.EXPENSES,
                                amount = 1.0,
                                description = "description",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 1,
                                minute = 1,
                                uri = ""
                            ),
                            Transaction(
                                category = Category(type = CategoryType.SALARY),
                                id = 2,
                                type = TransactionType.INCOME,
                                amount = 2.0,
                                description = "description2",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 2,
                                minute = 1,
                                uri = ""
                            )
                        )
                    ),
                    TransactionData(
                        date = Calendar.getInstance().timeInMillis,
                        totalAmount = 6.0,
                        transactions = listOf(
                            Transaction(
                                category = Category(type = CategoryType.GIFTS),
                                id = 1,
                                type = TransactionType.EXPENSES,
                                amount = 1.0,
                                description = "description",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 1,
                                minute = 1,
                                uri = ""
                            ),
                            Transaction(
                                category = Category(type = CategoryType.SALARY),
                                id = 2,
                                type = TransactionType.INCOME,
                                amount = 2.0,
                                description = "description2",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 2,
                                minute = 1,
                                uri = ""
                            )
                        )
                    ),
                    TransactionData(
                        date = Calendar.getInstance().timeInMillis,
                        totalAmount = -70.0,
                        transactions = listOf(
                            Transaction(
                                category = Category(type = CategoryType.GIFTS),
                                id = 1,
                                type = TransactionType.EXPENSES,
                                amount = 1.0,
                                description = "description",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 1,
                                minute = 1,
                                uri = ""
                            ),
                            Transaction(
                                category = Category(type = CategoryType.SALARY),
                                id = 2,
                                type = TransactionType.INCOME,
                                amount = 2.0,
                                description = "description2",
                                date =  Calendar.getInstance().timeInMillis,
                                hour = 2,
                                minute = 1,
                                uri = ""
                            )
                        )
                    ),
                )
            )
        )
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