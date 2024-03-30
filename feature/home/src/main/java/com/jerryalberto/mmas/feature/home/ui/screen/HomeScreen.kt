package com.jerryalberto.mmas.feature.home.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.constant.ColorConstant
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.core.ui.component.SpendFrequencyButton
import com.jerryalberto.mmas.feature.home.R
import com.jerryalberto.mmas.feature.home.ui.InputActivity
import com.jerryalberto.mmas.feature.home.ui.component.FabItem
import com.jerryalberto.mmas.feature.home.ui.component.IncomeExpenseBox2
import com.jerryalberto.mmas.feature.home.ui.component.MultiFloatingActionButton
import com.jerryalberto.mmas.feature.home.ui.component.PieChart
import com.jerryalberto.mmas.core.ui.component.TransactionBox
import com.jerryalberto.mmas.core.ui.component.TransactionHeader
import com.jerryalberto.mmas.feature.home.ui.component.DonutChart
import com.jerryalberto.mmas.feature.home.ui.component.DonutChartData
import com.jerryalberto.mmas.feature.home.ui.component.DonutChartDataCollection
import com.jerryalberto.mmas.feature.home.ui.component.PieChartWithText
import com.jerryalberto.mmas.feature.home.ui.uistate.HomeUIDataState
import com.jerryalberto.mmas.feature.home.ui.viewmodel.HomeScreenViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel
) {
    val uiState = homeScreenViewModel.uiState.collectAsState().value
    val context = LocalContext.current

    val floatingActionButton = @Composable {
        MultiFloatingActionButton (
            fabIcon = Icons.Rounded.Add,
            contentDescription = "Add Transaction",
            items = listOf(
                FabItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_income),
                    label = stringResource(id = R.string.feature_home_income),
                    bgColor = ColorConstant.IncomeGreen,
                    iconColor = Color.White,
                    onFabItemClicked = {
                        val intent = Intent(context, InputActivity::class.java)
                        intent.putExtra(InputActivity.PARAM_TYPE, TransactionType.INCOME.value)
                        context.startActivity(intent)
                    }
                ),
                FabItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_expenses),
                    label = stringResource(id = R.string.feature_home_expenses),
                    bgColor = ColorConstant.ExpensesRed,
                    iconColor = Color.White,
                    onFabItemClicked = {
                        val intent = Intent(context, InputActivity::class.java)
                        intent.putExtra(InputActivity.PARAM_TYPE, TransactionType.EXPENSES.value)
                        context.startActivity(intent)
                    }
                )
            )
        )
    }
    Scaffold (
        floatingActionButton = floatingActionButton
    ) { paddingValues ->
        HomeScreenContent(
            uiState = uiState,
            onTypeClicked = {
                homeScreenViewModel.getAmountByType(
                    it
                )
            }
        )
    }

}

@Composable
private fun HomeScreenContent(
    uiState : HomeUIDataState,
    onTypeClicked: (AccountBalanceDataType) -> Unit = {}
) {
    val accountBalanceDataTypeDesc = when (uiState.type) {
        AccountBalanceDataType.TOTAL -> stringResource(id = R.string.feature_home_total)
        AccountBalanceDataType.TODAY -> stringResource(id = R.string.feature_home_today)
        AccountBalanceDataType.MONTH -> stringResource(id = R.string.feature_home_month)
        AccountBalanceDataType.WEEK -> stringResource(id = R.string.feature_home_week)
    }
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.dimen16),
    ) {
        //account balance
        item {
            Text(
                text = stringResource(id = R.string.feature_home_title) + " (${accountBalanceDataTypeDesc})",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
            Text(
                text = uiState.totalAmountStr,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displayMedium,
            )
        }
        //income and expense box
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
            Row {
                IncomeExpenseBox2(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = MaterialTheme.dimens.dimen8),
                    bgColor = ColorConstant.IncomeGreen,
                    icon = ImageVector.vectorResource(R.drawable.ic_income),
                    title = stringResource(id = R.string.feature_home_income),
                    content = uiState.totalIncomeStr
                )
                IncomeExpenseBox2(
                    modifier = Modifier.weight(1f),
                    bgColor = ColorConstant.ExpensesRed,
                    icon = ImageVector.vectorResource(R.drawable.ic_expenses),
                    title = stringResource(id = R.string.feature_home_expenses),
                    content = uiState.totalExpensesStr
                )
            }
        }

        //chart
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
            PieChart(
                data = listOf(
                    Pair(ColorConstant.ExpensesRed, uiState.totalExpenses),
                    Pair(ColorConstant.IncomeGreen, uiState.totalIncome),
                )
            )
//            PieChartWithText(
//                chartDataList = listOf(
//                    Pair(ColorConstant.ExpensesRed, uiState.totalExpenses.toFloat()),
//                    Pair(ColorConstant.IncomeGreen, uiState.totalIncome.toFloat()),
//                )
//            )

//            DonutChart(data = DonutChartDataCollection(
//                    listOf(
//                        DonutChartData(
//                            amount = uiState.totalExpenses.toFloat(),
//                            color = ColorConstant.ExpensesRed,
//                            title = "title1"
//                        ),
//                        DonutChartData(
//                            amount = uiState.totalIncome.toFloat(),
//                            color = ColorConstant.IncomeGreen,
//                            title = "title2"
//                        ),
//                    )
//                )
//            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
        }

        //today, week, month
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
            LazyRow {
                item {
                    SpendFrequencyButton(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(id = R.string.feature_home_total),
                        selected = uiState.type == AccountBalanceDataType.TOTAL,
                        onClick = {
                            onTypeClicked.invoke(AccountBalanceDataType.TOTAL)
                        }
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
                }
                item {
                    SpendFrequencyButton(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(id = R.string.feature_home_today),
                        selected = uiState.type == AccountBalanceDataType.TODAY,
                        onClick = {
                            onTypeClicked.invoke(AccountBalanceDataType.TODAY)
                        }
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
                }
                item {
                    SpendFrequencyButton(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(id = R.string.feature_home_week),
                        selected = uiState.type == AccountBalanceDataType.WEEK,
                        onClick = {
                            onTypeClicked.invoke(AccountBalanceDataType.WEEK)
                        }
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
                }
                item {
                    SpendFrequencyButton(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(id = R.string.feature_home_month),
                        selected = uiState.type == AccountBalanceDataType.MONTH,
                        onClick = {
                            onTypeClicked.invoke(AccountBalanceDataType.MONTH)
                        }
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
                }
            }
        }
        //list of transaction
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
            TransactionHeader(
                leftText = stringResource(id = R.string.feature_home_recent_transaction),
                rightText = stringResource(id = R.string.feature_home_sell_all),
                rightTextOnClick = {

                }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
            TransactionBox(
                transactions = uiState.latestTransaction
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    MmasTheme {
        HomeScreenContent(
            uiState = HomeUIDataState()
        )
    }
}