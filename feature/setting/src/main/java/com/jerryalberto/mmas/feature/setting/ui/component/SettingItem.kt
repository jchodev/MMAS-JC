package com.jerryalberto.mmas.feature.setting.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String = "title",
    selectedValue: String = "selected Value",
    onClick : () -> Unit = {}
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            },
        headlineContent = {
            Text (
                color = MaterialTheme.colorScheme.onBackground,
                text = title,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = selectedValue,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Icon(
                    modifier = Modifier
                        .padding(start = MaterialTheme.dimens.dimen8)
                        .size(MaterialTheme.dimens.dimen24),
                    imageVector = Icons.Filled.ArrowForwardIos,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@DevicePreviews
@Composable
private fun SettingItemPreview(){
    MmasTheme {
        SettingItem()
    }
}