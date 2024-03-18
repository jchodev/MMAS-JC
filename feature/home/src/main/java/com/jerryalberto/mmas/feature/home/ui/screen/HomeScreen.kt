package com.jerryalberto.mmas.feature.home.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.feature.home.R
import com.jerryalberto.mmas.feature.home.ui.component.FabItem
import com.jerryalberto.mmas.feature.home.ui.component.IncomeExpenseBox2
import com.jerryalberto.mmas.feature.home.ui.component.MultiFloatingActionButton
import com.jerryalberto.mmas.feature.home.ui.component.SpendFrequencyButton
import com.jerryalberto.mmas.feature.home.ui.component.TransactionBox
import com.jerryalberto.mmas.feature.home.ui.component.VicoChart


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(

) {
    val floatingActionButton = @Composable {
//        FloatingActionButton(
//            onClick = {
//                //OnClick Method
//            },
//            containerColor = MaterialTheme.colorScheme.secondary,
//            shape = MaterialTheme.shapes.medium,
//        ) {
//            Icon(
//                imageVector = Icons.Rounded.Add,
//                contentDescription = "Add Transaction",
//                tint =  MaterialTheme.colorScheme.onSecondary,
//            )
//        }
        MultiFloatingActionButton (
            fabIcon = Icons.Rounded.Add,
            contentDescription = "Add Transaction",
            items = listOf(
                FabItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_income),
                    label = "Income",
                    bgColor = Color.Green,
                    iconColor = Color.White
                ),
                FabItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_expenses),
                    label = "Expenses",
                    bgColor = Color.Red,
                    iconColor = Color.White
                )
            )
        )
    }
    Scaffold (
        floatingActionButton = floatingActionButton
    ) { paddingValues ->
        HomeScreenContent()
    }

}

@Composable
private fun HomeScreenContent() {
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.dimen16),
    ) {
        //account balance
        item {
            Text(
                text = stringResource(id = R.string.feature_home_title),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
            Text(
                text = "$8999",
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
                    icon = ImageVector.vectorResource(R.drawable.ic_income)
                )
                IncomeExpenseBox2(
                    modifier = Modifier.weight(1f),
                    icon = ImageVector.vectorResource(R.drawable.ic_expenses)
                )
            }
        }
        //chart
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
            VicoChart()
        }
        //today, week, month
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
            Row {
                //today
                SpendFrequencyButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.feature_home_today),
                    selected = true
                )
                SpendFrequencyButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.feature_home_week),
                    selected = false
                )
                SpendFrequencyButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.feature_home_month),
                    selected = false
                )
            }
        }
        //list of transaction
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = stringResource(id = R.string.feature_home_recent_transaction),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.clickable {

                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = stringResource(id = R.string.feature_home_sell_all),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
            TransactionBox()
        }
    }
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    MmasTheme {
        HomeScreenContent()
    }
}