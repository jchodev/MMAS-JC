package com.jerryalberto.mmas.feature.home.ui.component

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.window.DialogProperties
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.designsystem.topbar.MmaTopBar
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.feature.home.model.CategoryDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    list: List<CategoryDisplay> = listOf(),
    properties: DialogProperties = DialogProperties().let {
        DialogProperties(
            dismissOnBackPress = it.dismissOnBackPress,
            dismissOnClickOutside = it.dismissOnClickOutside,
            securePolicy = it.securePolicy,
            usePlatformDefaultWidth = false,
        )
    },
    onCategorySelected: (CategoryDisplay) -> Unit = {}
) {

    BasicAlertDialog(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = onDismissRequest,
        properties = properties,
        content = {
            Scaffold(
                topBar = {
                    MmaTopBar(
                        modifier = Modifier
                            .shadow(elevation = MaterialTheme.dimens.dimen4),
                        title = "Select Category",
                        onCloseClick = onDismissRequest
                    )
                },
            ){ paddingValues ->
                LazyColumn(
                    Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                ) {
                    items(list) {
                        CategoryGroup(
                            category = it,
                            onCategorySelected = onCategorySelected
                        )
                    }
                }
            }
        }
    )
}