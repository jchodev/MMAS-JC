package com.jerryalberto.mmas.core.ui.component

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.model.data.Category

import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.core.ui.constants.ColorConstant
import com.jerryalberto.mmas.core.ui.ext.getImageVector
import com.jerryalberto.mmas.core.ui.ext.getString

@Composable
fun CategoryItem(
    bgColor: Color = ColorConstant.ExpensesRedBg,
    iconColor: Color = ColorConstant.ExpensesRed,
    textColor: Color = Color.Black,
    category: Category,
    onCategorySelected: (Category) -> Unit = {}
) {
    Column(
        modifier =
        Modifier
            .width(100.dp)
            .clickable {
                onCategorySelected.invoke(category)
            }
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CategoryIcon(
            icon =  category.type.getImageVector(),
            contentDescription = category.type.getString(),
            bgColor = bgColor,
            iconColor = iconColor
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))

        Text(
            text = category.type.getString(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = textColor
        )
    }
}


@Preview(showBackground = false)
@Composable
private fun CategoryItemPreview(){

    CategoryItem(
        category = Category(
            type = CategoryType.FOOD
        ),
    )
}

