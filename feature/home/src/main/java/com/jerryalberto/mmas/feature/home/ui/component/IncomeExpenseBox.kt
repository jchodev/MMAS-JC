package com.jerryalberto.mmas.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.jerryalberto.mmas.core.designsystem.text.AutoResizedText

import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.ui.component.CategoryIcon
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.feature.home.R


@Composable
fun IncomeExpenseBox(
    modifier: Modifier = Modifier,
    bgColor: Color = MaterialTheme.colorScheme.primary,
    icon: ImageVector = Icons.Filled.Home,
    title: String = "this is title",
    content: String = "this is content",
    textColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(color = bgColor)
            .padding(
                MaterialTheme.dimens.dimen16
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CategoryIcon(
            icon = icon,
            contentDescription = title,
            bgColor = textColor,
            iconColor = bgColor,
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
        Column (
            verticalArrangement = Arrangement.Center,
        ){
            Text(
                text = title,
                color = textColor,
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen4))
            AutoResizedText(
                text = content,
                color = textColor,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@DevicePreviews
@Composable
private fun IncomeExpenseBoxPreview(){
    MmasTheme {
        IncomeExpenseBox()
    }
}

@DevicePreviews
@Composable
private fun TwoBoxPreview(){
    MmasTheme {
        Row {
            IncomeExpenseBox(
                Modifier.weight(1f).padding(end = 10.dp),
                icon = ImageVector.vectorResource(R.drawable.ic_income)
            )
            IncomeExpenseBox(
                Modifier.weight(1f),
                icon = ImageVector.vectorResource(R.drawable.ic_expenses)
            )
        }
    }
}
