package com.jerryalberto.mmas.feature.home.ui.component

import android.R
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
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.window.DialogProperties
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.designsystem.topbar.MmaTopBar
import com.jerryalberto.mmas.core.domain.usecase.CategoriesUseCase
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.feature.home.model.CategoryDisplay
import com.jerryalberto.mmas.feature.home.model.toCategoryDisplay

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
        modifier = modifier.fillMaxSize(),
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


@Preview(apiLevel = 33, device = "spec:width=411dp,height=891dp", showBackground = true, showSystemUi = true)
@Composable
private fun CategorySelectDialogPreview(){
    MmasTheme {
        CategorySelectDialog(
            list = listOf(
                //food and beverage
                Category(
                    type = CategoryType.FOOD_AND_BEVERAGES,
                    items = listOf(
                        Category(
                            type = CategoryType.FOOD,
                        ),
                        Category(
                            type = CategoryType.BEVERAGES,
                        ),
                        Category(
                            type = CategoryType.GROCERIES,
                        )
                    )
                ),
                Category(CategoryType.OTHER)
            ).map {
                it.toCategoryDisplay()
            }

        )
    }
}
