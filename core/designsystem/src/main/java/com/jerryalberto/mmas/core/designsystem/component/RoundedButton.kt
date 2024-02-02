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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jerryalberto.mmas.core.designsystem.theme.Orange40

@Composable
fun RoundedButton(
    shape: RoundedCornerShape,
    border: BorderStroke,
    buttonColors: ButtonColors,
    iconLeft: ImageVector,
    title: String,
    fontWeight: FontWeight,
    fontStyle: FontStyle,
    fontSize: TextUnit,
    padding: Dp,
    onClick: () -> Unit = {}
) {

    Button(
        onClick = onClick, shape = shape, border = border, colors = buttonColors
    ) {
        Icon(
            imageVector = iconLeft, contentDescription = "Icon left"
        )
        Text(
            modifier = Modifier.padding(start = padding),
            text = title,
            textAlign = TextAlign.Left,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontSize = fontSize
        )
    }

}

@Preview(showBackground = true, backgroundColor = 0xFF0000F)
@Composable
private fun RoundedButtonPreview() {
    RoundedButton(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, Orange40),
        buttonColors = ButtonDefaults.buttonColors(
            contentColor = Color.White, containerColor = Orange40
        ),
        iconLeft = Icons.Default.DateRange,
        title = "Save",
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 20.sp,
        padding = 5.dp,
        onClick = {}
    )
}