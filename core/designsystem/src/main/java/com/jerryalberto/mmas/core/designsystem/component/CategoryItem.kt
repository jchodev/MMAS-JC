package com.jerryalberto.mmas.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CategoryItem(
    text: String,
    icon: ImageVector,
    onClick : () -> Unit = {}
) {
    Column(
        modifier =
            Modifier.width(80.dp)
                .clickable { onClick.invoke() }
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CategoryIcon(
            icon = icon,
            text = text
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = false)
@Composable
private fun CategoryItemPreview(){

    CategoryItem(
        text = "this is text",
        icon = Icons.Filled.Settings
    )
}

