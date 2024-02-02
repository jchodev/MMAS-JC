package com.jerryalberto.mmas.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jerryalberto.mmas.core.designsystem.theme.Orange40
import com.jerryalberto.mmas.core.designsystem.theme.roundedShapeScheme

@Composable
fun RoundedButton(
    shape: RoundedCornerShape,
    border: BorderStroke,
    buttonColors: ButtonColors,
    iconLeft: ImageVector,
    title: String,
    textStyle: TextStyle,
    padding: Dp,
    contentDescription: String,
    onClick: () -> Unit = {}
) {

    Button(
        onClick = onClick, shape = shape, border = border, colors = buttonColors
    ) {
        Icon(
            imageVector = iconLeft, contentDescription = contentDescription
        )
        Text(
            modifier = Modifier.padding(start = padding),
            text = title,
            textAlign = TextAlign.Left,
            style = textStyle
        )
    }

}

@Preview(showBackground = true, backgroundColor = 0xFF0000F)
@Composable
private fun RoundedButtonPreview() {
    RoundedButton(
        shape = MaterialTheme.roundedShapeScheme.medium,
        border = BorderStroke(2.dp, Orange40),
        buttonColors = ButtonDefaults.buttonColors(
            contentColor = Color.White, containerColor = Orange40
        ),
        iconLeft = Icons.Default.DateRange,
        title = "Save",
        textStyle = MaterialTheme.typography.titleMedium,
        padding = 5.dp,
        contentDescription = "Icon left",
        onClick = {}
    )
}