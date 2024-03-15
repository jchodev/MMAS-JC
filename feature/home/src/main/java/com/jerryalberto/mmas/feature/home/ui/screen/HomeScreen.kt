package com.jerryalberto.mmas.feature.home.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.feature.home.R
import com.jerryalberto.mmas.feature.home.ui.component.IncomeExpenseBox2
import com.jerryalberto.mmas.feature.home.ui.component.SpendFrequencyButton
import com.jerryalberto.mmas.feature.home.ui.component.TransactionBox
import com.jerryalberto.mmas.feature.home.ui.component.VicoChart

@Composable
fun HomeScreen() {
    HomeScreenContent()
}

@Composable
private fun HomeScreenContent() {
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.spacing16),
    ) {
        //account balance
        item {
            Text(
                text = stringResource(id = R.string.feature_home_title),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacing8))
            Text(
                text = "$8999",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displayMedium,
            )
        }
        //income and expense box
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacing16))
            Row {
                IncomeExpenseBox2(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = MaterialTheme.dimens.spacing8)
                )
                IncomeExpenseBox2(
                    modifier = Modifier.weight(1f)
                )
            }
        }
        //chart
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacing16))
            VicoChart()
        }
        //today, week, month
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacing8))
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
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacing8))
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