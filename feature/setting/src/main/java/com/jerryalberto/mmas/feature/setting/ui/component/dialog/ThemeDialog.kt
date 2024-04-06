package com.jerryalberto.mmas.feature.setting.ui.component.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.ui.ext.convertMillisToDate
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.feature.setting.R

import java.util.Calendar


@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    dataList: List<String> = listOf(),
    onDismissRequest: () -> Unit = {},
    onSelected: (item: String) -> Unit = {},
){
    val itemContent = @Composable { str: String -> // Specify type and default
        ListItem(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onSelected(str)
                },
            headlineContent = {
                Column{
                    Text(
                        text =  str,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            },
        )
    }

    SelectDialog(
        modifier = modifier,
        fullList = dataList,
        onDismissRequest = onDismissRequest,
        title = stringResource(id = R.string.feature_setting_select_theme),
        itemContent = {
            itemContent(it)
        },
        supportSearch = false,
    )
}

@DevicePreviews
@Composable
private fun ThemeDialogPreview(){
    MmasTheme {
        ThemeDialog(
            dataList = listOf(
                "Default",
                "Light",
                "Dark"
            )
        )
    }
}