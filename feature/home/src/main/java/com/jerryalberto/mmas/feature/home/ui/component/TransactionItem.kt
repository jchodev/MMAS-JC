package com.jerryalberto.mmas.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.component.CategoryIcon
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
) {
    Row (
       modifier = modifier.padding(MaterialTheme.dimens.dimen16)
    ) {
        CategoryIcon(
            contentDescription = "this is text",
            icon = Icons.Filled.Settings
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
        Column (
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
        ){
            Text(
                text = "title",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen4))
            Text(
                text = "content",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Column (
            verticalArrangement = Arrangement.Center,
        ){
            Text(
                text = "- 120",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.End)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen4))
            Text(
                text = "22:11 12 Mar",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionItemPreview(
    modifier: Modifier = Modifier,
) {
    MmasTheme {
        TransactionItem()
    }
}