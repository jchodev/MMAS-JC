package com.jerryalberto.mmas.feature.home.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme


@Composable
fun SpendFrequencyButton(
    modifier: Modifier = Modifier,
    selected: Boolean = true,
    onClick: () -> Unit = {},
    text: String = "text",
) {
    Button(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor  =
            if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                Color.Transparent
            },
        ),
        onClick = onClick
    ) {
        Text(
            color =
                if (selected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            ,
            text = text,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SpendFrequencyButtonPreview(){
    MmasTheme {
        SpendFrequencyButton(
            modifier = Modifier.width(100.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SpendFrequencyButtonNonSelectedPreview(){
    MmasTheme {
        SpendFrequencyButton(
            selected = false
        )
    }
}
