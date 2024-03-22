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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.feature.home.model.CategoryDisplay
import com.jerryalberto.mmas.feature.home.model.toCategoryDisplay
import com.jerryalberto.mmas.feature.home.ui.helper.UiHelper

@Composable
fun TransactionItem(
    uiHelper: UiHelper = UiHelper(),
    modifier: Modifier = Modifier,
    transaction: Transaction,
) {
    val category = transaction.category?.toCategoryDisplay() ?: CategoryDisplay(
        CategoryType.ACCESSORIES
    )
    
    val colors = uiHelper.getColorByTransactionType(transaction.type)

    Row (
       modifier = modifier.padding(MaterialTheme.dimens.dimen16),
       verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIcon(
            size = MaterialTheme.dimens.dimen56,
            icon = ImageVector.vectorResource(category.imageResId),
            contentDescription = stringResource(id = category.stringResId),
            iconColor = colors.first,
            bgColor = colors.second
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen16))
        Column (
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
        ){

            Text(
                text = stringResource(id = category.stringResId),
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
                text = uiHelper.formatAmount(
                    amount = transaction.amount,
                    type = transaction.type ?: TransactionType.EXPENSES
                ),
                color = colors.first,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.End)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen4))
            Text(
                text = uiHelper.displayDateTime(
                    date = transaction.date,
                    hour = transaction.hour,
                    minute = transaction.minute
                ),
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
        TransactionItem(
            transaction = Transaction(
                type = TransactionType.EXPENSES,
                amount = 10.0,
                category = Category(
                    type = CategoryType.ACCESSORIES
                ),
                date = UiHelper().getCurrentDateDateMillis(),
                hour = 1,
                minute = 12,
                description = "this is desc",
                uri = "",
            )
        )
    }
}