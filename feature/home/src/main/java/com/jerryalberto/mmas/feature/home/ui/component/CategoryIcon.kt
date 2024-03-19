package com.jerryalberto.mmas.feature.home.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.jerryalberto.mmas.core.designsystem.theme.dimens

@Composable
fun CategoryIcon(
    bgColor: Color = Color.Gray,
    size: Dp = MaterialTheme.dimens.dimen48,
    contentDescription: String = "",
    icon: ImageVector,
) {
    Surface(
        modifier = Modifier.size(size), // Adjust size as needed
        shape = CircleShape,
        color = bgColor
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize().padding(MaterialTheme.dimens.dimen8)
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun CategoryIconPreview(){
    CategoryIcon(
        contentDescription = "this is text",
        icon = Icons.Filled.Settings
    )
}

