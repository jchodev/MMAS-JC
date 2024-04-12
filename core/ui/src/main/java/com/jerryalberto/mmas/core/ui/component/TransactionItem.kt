package com.jerryalberto.mmas.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.core.ui.ext.displayDateTime
import com.jerryalberto.mmas.core.ui.ext.formatAmount
import com.jerryalberto.mmas.core.ui.ext.getColors
import com.jerryalberto.mmas.core.ui.ext.getImageVector
import com.jerryalberto.mmas.core.ui.ext.getString
import com.jerryalberto.mmas.core.ui.helper.UiHelper


import java.util.Calendar

@Composable
fun TransactionItem(
    setting: Setting = Setting(),
    showTimeOnly: Boolean = true,
    modifier: Modifier = Modifier,
    transaction: Transaction,
) {
    val category = transaction.category ?: Category(
        CategoryType.ACCESSORIES
    )

    val colors = transaction.type.getColors()
    Box(
        modifier = modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        ListItem(
            modifier = Modifier.fillMaxSize(),
            leadingContent = {
                CategoryIcon(
                    size = MaterialTheme.dimens.dimen56,
                    icon = category.type.getImageVector(),
                    contentDescription = category.type.getString(),
                    iconColor = colors.first,
                    bgColor = colors.second
                )
            },
            headlineContent = {
                Column (
                    verticalArrangement = Arrangement.Center,
                ){
                    Text(
                        text = category.type.getString(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen4))
                    Text(
                        text = transaction.description,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            },
            trailingContent = {
                Column (
                    verticalArrangement = Arrangement.Center,
                ){
                    Text(
                        text = transaction.formatAmount(setting = setting),
                        color = colors.first,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.End)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen4))
                    Text(
                        text = transaction.displayDateTime(setting = setting, showTimeOnly = showTimeOnly),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        )
    }
}

@Composable
fun TransactionItemOld(
    setting: Setting = Setting(),
    showTimeOnly: Boolean = true,
    modifier: Modifier = Modifier,
    transaction: Transaction,
) {
    val category = transaction.category ?: Category(
        CategoryType.ACCESSORIES
    )
    
    val colors = transaction.type.getColors()
    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        Row (
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.background),
            //.padding(MaterialTheme.dimens.dimen16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryIcon(
                size = MaterialTheme.dimens.dimen56,
                icon = category.type.getImageVector(),
                contentDescription = category.type.getString(),
                iconColor = colors.first,
                bgColor = colors.second
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen16))
            Column (
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ){
                Text(
                    text = category.type.getString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen4))
                Text(
                    text = transaction.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Column (
                verticalArrangement = Arrangement.Center,
            ){
                Text(
                    text = transaction.formatAmount(setting = setting),
                    color = colors.first,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen4))
                Text(
                    text = transaction.displayDateTime(setting = setting, showTimeOnly = showTimeOnly),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun TransactionItemPreview(
    modifier: Modifier = Modifier,
) {
    MmasTheme {
        TransactionItem(
            transaction = Transaction(
                type = TransactionType.EXPENSES,
                amount = 10.0,
                category = Category(
                    type = CategoryType.ACCESSORIES
                ),
                date = Calendar.getInstance().timeInMillis,
                hour = 1,
                minute = 12,
                description = "this is desc",
                uri = "",
            )
        )
    }
}