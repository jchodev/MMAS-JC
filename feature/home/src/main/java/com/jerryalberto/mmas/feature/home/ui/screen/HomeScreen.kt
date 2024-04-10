package com.jerryalberto.mmas.feature.home.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.jerryalberto.mmas.core.ui.constants.ColorConstant
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType

import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.core.ui.component.SpendFrequencyButton
import com.jerryalberto.mmas.feature.home.R
import com.jerryalberto.mmas.feature.home.ui.component.FabItem
import com.jerryalberto.mmas.feature.home.ui.component.MultiFloatingActionButton
import com.jerryalberto.mmas.feature.home.ui.component.PieChart
import com.jerryalberto.mmas.core.ui.component.TransactionBox
import com.jerryalberto.mmas.core.ui.component.TransactionHeader
import com.jerryalberto.mmas.core.ui.ext.formatAmount
import com.jerryalberto.mmas.core.ui.ext.getString
import com.jerryalberto.mmas.core.ui.navigation.MainRoute
import com.jerryalberto.mmas.feature.home.ui.component.IncomeExpenseBox
import com.jerryalberto.mmas.feature.home.ui.dialog.InputTransactionDialog


import com.jerryalberto.mmas.feature.home.ui.uistate.HomeUIDataState
import com.jerryalberto.mmas.feature.home.ui.viewmodel.HomeScreenViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    setting: Setting,
    mainNavController: NavHostController,
) {
    val uiState = homeScreenViewModel.uiState.collectAsStateWithLifecycle().value

    var selectedTransactionType by remember { mutableStateOf(TransactionType.INCOME) }

    //inputDialog
    var openInputDialog by remember { mutableStateOf(false) }
    if (openInputDialog){
        InputTransactionDialog(
            setting = setting,
            onDismissRequest = { openInputDialog = false },
            transactionType = selectedTransactionType
        )
    }

    val floatingActionButton = @Composable {
        MultiFloatingActionButton (
            fabIcon = Icons.Rounded.Add,
            contentDescription = stringResource(id = R.string.feature_home_add_transaction),
            items = listOf(
                FabItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_income),
                    label = stringResource(id = R.string.feature_home_income),
                    bgColor = ColorConstant.IncomeGreen,
                    iconColor = Color.White,
                    onFabItemClicked = {
                        selectedTransactionType = TransactionType.INCOME
                        openInputDialog = true
                    }
                ),
                FabItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_expenses),
                    label = stringResource(id = R.string.feature_home_expenses),
                    bgColor = ColorConstant.ExpensesRed,
                    iconColor = Color.White,
                    onFabItemClicked = {
                        selectedTransactionType = TransactionType.EXPENSES
                        openInputDialog = true
                    }
                )
            )
        )
    }
    Box(modifier = Modifier.fillMaxSize()){
        Scaffold (
            floatingActionButton = floatingActionButton
        ) { paddingValues ->
            HomeScreenContent(
                uiState = uiState,
                setting = setting,
                onTypeClicked = {
                    homeScreenViewModel.getAmountByType(
                        it
                    )
                },
                onSeeAllClick = {
                    mainNavController.navigate(MainRoute.TransactionScreen.route)
                }
            )
        }
    }


}

@Composable
private fun HomeScreenContent(
    setting: Setting = Setting(),
    uiState : HomeUIDataState,
    onTypeClicked: (AccountBalanceDataType) -> Unit = {},
    onSeeAllClick: () -> Unit = {},
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
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.feature_home_title) + " (${accountBalanceDataTypeDesc})",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
                Text(
                    text = uiState.getTotalAmount().formatAmount(setting = setting),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displayMedium,
                )
            }
        }
        //income and expense box
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
            Row {
                IncomeExpenseBox(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = MaterialTheme.dimens.dimen8),
                    bgColor = ColorConstant.IncomeGreen,
                    textColor = Color.White,
                    icon = ImageVector.vectorResource(R.drawable.ic_income),
                    title = stringResource(id = R.string.feature_home_income),
                    content = uiState.totalIncome.formatAmount(setting = setting, withCurrencySymbol = true)
                )
                IncomeExpenseBox(
                    modifier = Modifier.weight(1f),
                    bgColor = ColorConstant.ExpensesRed,
                    textColor = Color.White,
                    icon = ImageVector.vectorResource(R.drawable.ic_expenses),
                    title = stringResource(id = R.string.feature_home_expenses),
                    content = uiState.totalExpenses.formatAmount(setting = setting, withCurrencySymbol = true)
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
                rightText = stringResource(id = R.string.feature_home_see_all),
                rightTextOnClick = onSeeAllClick
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
            if (uiState.latestTransaction.isNotEmpty()) {
                TransactionBox(
                    showTimeOnly = false,
                    setting = setting,
                    transactions = uiState.latestTransaction
                )
            }
            else {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.feature_home_no_transaction),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun HomeScreenContentPreview() {
    val floatingActionButton = @Composable {
        MultiFloatingActionButton (
            fabIcon = Icons.Rounded.Add,
            contentDescription = stringResource(id = R.string.feature_home_add_transaction),
            items = listOf(
                FabItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_income),
                    label = stringResource(id = R.string.feature_home_income),
                    bgColor = ColorConstant.IncomeGreen,
                    iconColor = Color.White,
                    onFabItemClicked = {

                    }
                ),
                FabItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_expenses),
                    label = stringResource(id = R.string.feature_home_expenses),
                    bgColor = ColorConstant.ExpensesRed,
                    iconColor = Color.White,
                    onFabItemClicked = {

                    }
                )
            )
        )
    }

    MmasTheme {
        Box(modifier = Modifier.fillMaxSize()){
            Scaffold (
                floatingActionButton = floatingActionButton
            ) {
                HomeScreenContent(
                    uiState = HomeUIDataState()
                )
            }
        }
    }
}