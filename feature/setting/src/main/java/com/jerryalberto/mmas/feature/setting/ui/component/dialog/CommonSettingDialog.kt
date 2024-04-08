package com.jerryalberto.mmas.feature.setting.ui.component.dialog

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.jerryalberto.mmas.feature.setting.R


@Composable
fun <T> CommonSettingDialog(
    modifier: Modifier = Modifier,
    defaultItem: T,
    itemList: List<T> = listOf(),
    onDismissRequest: () -> Unit = {},
    title: String =  "title is title",
    onItemSelected: (item: T) -> Unit = {},
    headlineContent: @Composable (item: T) -> Unit,
) {
    BaseSelectDialog(
        modifier = modifier,
        fullList = itemList,
        onDismissRequest = onDismissRequest,
        title = title,
        onItemSelected = onItemSelected,
        headlineContent = headlineContent,
        trailingContent = {
            if (it ==defaultItem){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_selected),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                )
            }
        },

        supportSearch = false,
    )
}