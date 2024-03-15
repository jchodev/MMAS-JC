package com.jerryalberto.mmas.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme


@Composable
fun TransactionBox(
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(color = MaterialTheme.colorScheme.surfaceVariant)


    ) {
        //items
        TransactionItem()
        TransactionItem()
        TransactionItem()
        TransactionItem()
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