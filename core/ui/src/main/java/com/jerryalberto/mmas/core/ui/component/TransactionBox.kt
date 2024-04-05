package com.jerryalberto.mmas.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionType
import java.util.Calendar


@Composable
fun TransactionBox(
    modifier: Modifier = Modifier,
    setting: Setting = Setting(),
    transactions: List<Transaction> = listOf(),
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            //.background(color = MaterialTheme.colorScheme.surfaceVariant)


    ) {
        //items
        transactions.forEach {
            TransactionItem(
                setting = setting,
                transaction = it
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionBoxPreview(
    modifier: Modifier = Modifier,
) {
    MmasTheme {
        TransactionBox(
            transactions = listOf(
                Transaction(
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
                    id = 2,
                    type = TransactionType.EXPENSES,
                    amount = 2.0,
                    description = "description2",
                    date =  Calendar.getInstance().timeInMillis,
                    hour = 2,
                    minute = 1,
                    uri = ""
                )
            )
        )
    }
}