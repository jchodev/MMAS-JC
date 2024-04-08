package com.jerryalberto.mmas.feature.setting.ui.component.dialog

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.model.data.ThemeType
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.feature.setting.R
import com.jerryalberto.mmas.feature.setting.ui.ext.getString


@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    itemList: List<ThemeType> = listOf(),
    onDismissRequest: () -> Unit = {},
    defaultItem: ThemeType,
    onItemSelected: (item: ThemeType) -> Unit = {},
){

    CommonSettingDialog(
        onItemSelected = onItemSelected,
        modifier = modifier,
        itemList = itemList,
        onDismissRequest = onDismissRequest,
        title = stringResource(id = R.string.feature_setting_select_theme),
        headlineContent = {
            Text(
                text =  it.getString(),
                style = MaterialTheme.typography.titleMedium,
            )
        },
        defaultItem = defaultItem
    )
}

@DevicePreviews
@Composable
private fun ThemeDialogPreview(){
    MmasTheme {
        ThemeDialog(
            itemList = listOf(
                ThemeType.LIGHT,
                ThemeType.DARK,
            ),
            defaultItem = ThemeType.DARK
        )
    }
}

