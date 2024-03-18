package com.jerryalberto.mmas.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerryalberto.mmas.core.designsystem.R
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType

@Composable
fun CategoryItem(
    textColor: Color = Color.Black,
    category: Category,
    onClick : (Category) -> Unit = {}
) {
    Column(
        modifier =
            Modifier.width(80.dp)
                .clickable { onClick.invoke(category) }
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CategoryIcon(
            icon =  ImageVector.vectorResource(category.imageResId),
            contentDescription = stringResource(id = category.stringResId)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))

        Text(
            text = stringResource(id = category.stringResId),
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
            type = CategoryType.FOOD,
            imageResId = android.R.mipmap.sym_def_app_icon,
            stringResId = android.R.string.copy
        ),
    )
}

