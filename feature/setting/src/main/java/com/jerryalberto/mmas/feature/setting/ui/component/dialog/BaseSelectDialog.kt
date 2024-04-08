package com.jerryalberto.mmas.feature.setting.ui.component.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.jerryalberto.mmas.core.designsystem.R
import com.jerryalberto.mmas.core.designsystem.textfield.TopBarSearchTextField
import com.jerryalberto.mmas.core.designsystem.theme.dimens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> BaseSelectDialog(
    modifier: Modifier = Modifier,
    title: String = "Select",
    supportSearch: Boolean = true,
    searchValue: String ="",
    fullList: List<T> = listOf(),
    filteredItems: List<T> = listOf(),
    onDismissRequest: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
    properties: DialogProperties = DialogProperties().let {
        DialogProperties(
            dismissOnBackPress = it.dismissOnBackPress,
            dismissOnClickOutside = it.dismissOnClickOutside,
            securePolicy = it.securePolicy,
            usePlatformDefaultWidth = false,
        )
    },
    onItemSelected: (item: T) -> Unit = {},

    //item Content
    leadingContent: (@Composable (item: T) -> Unit)? = null,
    headlineContent: @Composable (item: T) -> Unit,
    trailingContent: (@Composable (item: T) -> Unit)? = null,
) {

    var isSearch by remember { mutableStateOf(false) }

    val itemContent = @Composable { item: T -> // Specify type and default
        ListItem(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemSelected(item)
                },
            leadingContent = {
                leadingContent?.let {
                    leadingContent.invoke(item)
                }
            },
            headlineContent = {
                headlineContent.invoke(item)
            },
            trailingContent = {
                trailingContent?.let {
                    trailingContent.invoke(item)
                }
            }
        )
    }

    BasicAlertDialog(
        modifier = modifier.fillMaxSize(),
        onDismissRequest = onDismissRequest,
        properties = properties,
        content = {
            Surface(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            modifier = Modifier.shadow(elevation = MaterialTheme.dimens.dimen4),
                            title = {
                                if (isSearch) {
                                    TopBarSearchTextField(
                                        searchValue = searchValue,
                                        onValueChange = onValueChange
                                    )
                                } else {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    onDismissRequest()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBackIosNew,
                                        contentDescription = stringResource(id = R.string.close),
                                    )
                                }
                            },
                            actions = {
                                if (supportSearch) {
                                    IconButton(onClick = {
                                        isSearch = !isSearch
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            },
                        )
                    }
                ) { paddingValues ->
                    LazyColumn(
                        Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                    ) {
                        val shownData =
                            if (searchValue.isEmpty()) {
                                fullList
                            } else {
                                filteredItems
                            }
                        items(shownData) {
                            itemContent(it)
                        }
                    }
                }
            }
        }
    )
}