package com.jerryalberto.mmas.feature.transaction.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
    onYearMonthItemClick : (YearMonthItem) -> Unit = {}
){
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MmaTopBar(
                modifier = Modifier
                    .shadow(elevation = MaterialTheme.dimens.dimen4),
                title = "this is title",
                //onCloseClick = onTopBarLeftClick
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
                transactionData = uiState.transactionList
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