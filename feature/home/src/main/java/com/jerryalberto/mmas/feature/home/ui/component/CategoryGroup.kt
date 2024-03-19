package com.jerryalberto.mmas.feature.home.ui.component

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.feature.home.model.CategoryDisplay

@Composable
fun CategoryGroup(
    modifier: Modifier = Modifier.fillMaxWidth(),
    category: CategoryDisplay,
    onCategorySelected: (CategoryDisplay) -> Unit = {}
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            //.background(color = Color.Green) // for testing
            .padding(MaterialTheme.dimens.dimen16)
    ) {

        //row1
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(
                        topStart = MaterialTheme.dimens.dimen16,
                        topEnd = MaterialTheme.dimens.dimen16
                    )
                )
        ) {
            // Content for the first row
            Row (
                modifier = Modifier
                    .padding(MaterialTheme.dimens.dimen16)
                    .clickable {
                        onCategorySelected.invoke(category)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                CategoryIcon(
                    size = MaterialTheme.dimens.dimen56,
                    icon = ImageVector.vectorResource(category.imageResId),
                    contentDescription = stringResource(id = category.stringResId)
                )

                Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))

                Text(
                    text = stringResource(id = category.stringResId),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onSecondary
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        //row 2
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RoundedCornerShape(
                        bottomStart = MaterialTheme.dimens.dimen16,
                        bottomEnd = MaterialTheme.dimens.dimen16
                    )
                )
                .padding(
                    start = MaterialTheme.dimens.dimen8,
                    top = MaterialTheme.dimens.dimen16,
                    end = MaterialTheme.dimens.dimen8,
                    bottom = MaterialTheme.dimens.dimen24
                )
        ) {
            LazyRow {
                items(category.items.size) { index ->
                    val item = category.items[index]
                    CategoryItem(
                        category = item,
                        textColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        onCategorySelected = onCategorySelected
                    )

                    if (index < category.items.size - 1) {
                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryGroupPreview(){
    MmasTheme {
        CategoryGroup(
            category = CategoryDisplay(
                type = CategoryType.FOOD_AND_BEVERAGES,
                imageResId = R.mipmap.sym_def_app_icon,
                stringResId = R.string.copy,
                items = listOf(
                    CategoryDisplay(
                        type = CategoryType.FOOD,
                        imageResId = R.mipmap.sym_def_app_icon,
                        stringResId = R.string.copy
                    ),
                    CategoryDisplay(
                        type = CategoryType.BEVERAGES,
                        imageResId = R.mipmap.sym_def_app_icon,
                        stringResId = R.string.copy
                    ),
                    CategoryDisplay(
                        type = CategoryType.GROCERIES,
                        imageResId = R.mipmap.sym_def_app_icon,
                        stringResId = R.string.copy
                    )
                )
            ),

        )
    }
}

