package com.jerryalberto.mmas.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ItemRow(
    title: String,
    iconLeft: ImageVector,
    iconRight: ImageVector,
    textAlign: TextAlign,
    textStyle: TextStyle,
    padding: Dp,
    onClick: () -> Unit = {}
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(padding)
            .background(color = Color.White)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .weight(1f)
                .padding(padding),
            imageVector = iconLeft, contentDescription = "Icon left"
        )
        Text(
            modifier = Modifier
                .weight(7f)
                .padding(padding),
            text = title,
            textAlign = textAlign,
            style = textStyle
        )
        Icon(
            modifier = Modifier
                .weight(1f)
                .padding(padding),
            imageVector = iconRight,
            contentDescription = "Icon right"
        )
    }

}

@Preview(showBackground = false)
@Composable
private fun ItemRowPreview() {
    ItemRow(
        title = "Category",
        iconLeft = Icons.Default.DateRange,
        iconRight = Icons.Default.KeyboardArrowRight,
        textAlign = TextAlign.Left,
        textStyle = MaterialTheme.typography.titleMedium,
        padding = 10.dp
    )
}