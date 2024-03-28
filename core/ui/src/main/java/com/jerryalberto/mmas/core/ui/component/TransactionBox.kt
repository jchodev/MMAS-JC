package com.jerryalberto.mmas.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.model.data.Transaction


@Composable
fun TransactionBox(
    modifier: Modifier = Modifier,
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
        TransactionBox()
    }
}