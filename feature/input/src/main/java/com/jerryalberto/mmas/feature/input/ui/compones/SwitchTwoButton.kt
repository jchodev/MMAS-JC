package com.jerryalberto.mmas.feature.input.ui.compones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens

@Composable
fun SwitchTwoButton(
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,

    //botton1
    button1Text:String = "this is button 1 test",
    button1OnClick: () -> Unit = {},
    button1Selected: Boolean = true,
    //button2
    button2Text: String  = "this is button 2 test",
    button2OnClick: () -> Unit = {},
) {
    val selectedBg = MaterialTheme.colorScheme.background
    val nonSelectBg = Color.Transparent

    val selectedTextColor = MaterialTheme.colorScheme.primary
    val nonSelectedTextColor = MaterialTheme.colorScheme.onPrimary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background( color = MaterialTheme.colorScheme.primary)
            .padding(MaterialTheme.dimens.dimen8)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //button 1
        Button(
            modifier = Modifier
                .weight(weight = 1f)
                .background(
                    color = if (button1Selected)
                        selectedBg
                    else
                        nonSelectBg,
                    shape = CircleShape
                )
                .fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            onClick = button1OnClick,
        ) {
            Text(
                text = button1Text,
                color = if (button1Selected) {
                    selectedTextColor
                } else {
                    nonSelectedTextColor
                },
                style = textStyle,
                textAlign = TextAlign.Center,
            )
        }

        val button2Selected = !button1Selected
        Button(
            modifier = Modifier
                .weight(weight = 1f)
                .background(
                    color = if (button2Selected)
                        selectedBg
                    else
                        nonSelectBg,
                    shape = CircleShape
                )
                .fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            onClick = button2OnClick,
        ) {
            Text(
                text = button2Text,
                color = if (button2Selected) {
                    selectedTextColor
                } else {
                    nonSelectedTextColor
                },
                style = textStyle,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(showBackground = true)
@Composable
private fun SwitchTwoButtonPreview(){
    MmasTheme {
        SwitchTwoButton()
    }
}

@Preview(name = "Light Mode")
@Preview(showBackground = true)
@Composable
private fun SwitchTwoButtonSingleLinePreview(){
    MmasTheme {
        SwitchTwoButton(
            button1Text = "Income",
            button2Text = "Expense",
        )
    }
}

@Preview(name = "Light Mode")
@Preview(showBackground = true)
@Composable
private fun SwitchTwoButtonTextStylePreview(){
    MmasTheme {
        SwitchTwoButton(
            button1Text = "Income",
            button2Text = "Expense",
            textStyle = MaterialTheme.typography.titleLarge
        )
    }
}