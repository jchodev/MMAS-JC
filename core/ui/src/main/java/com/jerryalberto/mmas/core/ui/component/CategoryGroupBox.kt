package com.jerryalberto.mmas.core.ui.component


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
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.constant.ColorConstant
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.core.ui.ext.getImageVector
import com.jerryalberto.mmas.core.ui.ext.getString

@Composable
fun CategoryGroupBox(
    modifier: Modifier = Modifier.fillMaxWidth(),
    category: Category,
    isExpenses: Boolean = true,
    onCategorySelected: (Category) -> Unit = {}
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            //.background(color = Color.Green) // for testing
            .padding(MaterialTheme.dimens.dimen16)
    ) {

        if (category.items.isEmpty()) {
            CategorySingleGroup(
                modifier = modifier,
                category = category,
                onCategorySelected = onCategorySelected
            )
        } else {
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
                Row(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.dimen16)
                        .clickable {
                            onCategorySelected.invoke(category)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    CategoryIcon(
                        size = MaterialTheme.dimens.dimen56,
                        icon = category.type.getImageVector(),
                        contentDescription = category.type.getString(),
                        bgColor = if (isExpenses) ColorConstant.ExpensesRedBg else ColorConstant.IncomeGreenBg,
                        iconColor = if (isExpenses) ColorConstant.ExpensesRed else ColorConstant.IncomeGreen,
                    )

                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))

                    Text(
                        text = category.type.getString(),
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
                            bgColor = if (isExpenses) ColorConstant.ExpensesRedBg else ColorConstant.IncomeGreenBg,
                            iconColor = if (isExpenses) ColorConstant.ExpensesRed else ColorConstant.IncomeGreen,
                            category = item,
                            textColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            onCategorySelected = onCategorySelected,
                        )

                        if (index < category.items.size - 1) {
                            Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
                        }
                    }
                }
            }
        }
    }
}

@Preview(apiLevel = 33, device = "spec:width=411dp,height=891dp", showBackground = true, showSystemUi = true)
@Composable
private fun CategoryGroupBoxPreview(){
    MmasTheme {
        CategoryGroupBox(
            category = Category(
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
        )
    }
}

@Preview
@Composable
private fun CategoryGroupSinglePreview(){
    MmasTheme {
        CategoryGroupBox(
            category = Category(
                type = CategoryType.FOOD_AND_BEVERAGES,
            ),
        )
    }
}


@Composable
private fun CategorySingleGroup(
    modifier: Modifier = Modifier.fillMaxWidth(),
    category: Category,
    onCategorySelected: (Category) -> Unit = {},
    bgColor: Color = ColorConstant.ExpensesRedBg,
    iconColor: Color = ColorConstant.ExpensesRed
){

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium,
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
                icon = category.type.getImageVector(),
                contentDescription = category.type.getString(),
                bgColor = bgColor,
                iconColor = iconColor
            )

            Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))

            Text(
                text = category.type.getString(),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onSecondary
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}