package com.jerryalberto.mmas.core.designsystem.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import com.jerryalberto.mmas.core.designsystem.R
import com.jerryalberto.mmas.core.designsystem.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MmaTopBar(
    modifier: Modifier = Modifier,
    title: String = "title",
    onCloseClick: () -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
) {
    CenterAlignedTopAppBar(
        colors = colors,
        modifier = modifier,
        title = {
            Text(
                //modifier = Modifier.offset(y = (-2).dp),
                text = title,
                style = MaterialTheme.typography.titleMedium

            )
        },
        navigationIcon = {
            IconButton(onClick = {
                onCloseClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = stringResource(id = R.string.close),
                )
            }
        }
    )
}