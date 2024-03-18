package com.jerryalberto.mmas.core.designsystem.edittext


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.text.ErrorText
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme



@Composable
fun MmasTextEdit(
    modifier : Modifier = Modifier,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    value: String = "",
    error: String? = null,
    onValueChange: (String) -> Unit = {},
    placeHolder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxChar: Int = Int.MAX_VALUE,
) {
    Column (modifier = modifier.fillMaxWidth()) {

        OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = readOnly,
                singleLine = true,
                value = value,
                colors = colors,
                onValueChange = {
                    if (it.length <= maxChar) {
                        onValueChange.invoke(it)
                    }
                },
                placeholder = {
                    Text(
                        text = placeHolder,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                trailingIcon = trailingIcon,
                leadingIcon = leadingIcon,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),

                visualTransformation = visualTransformation,
            )
        error?.let {
            ErrorText(error = error)
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFF0000FF)
@Composable
private fun MmasTextEditWithTextValuePreview(){
    MmasTheme {
        MmasTextEdit(
            modifier = Modifier.fillMaxWidth(),
            value = "this is text",
            placeHolder = "this is place holder"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0000FF)
@Composable
private fun GleyTextEditWithPlaceHolderPreview(){
    MmasTheme {
        MmasTextEdit(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = "this is place holder"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0000FF)
@Composable
private fun GleyTextEditWithErrorPreview(){
    MmasTheme {
        MmasTextEdit(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = "this is place holder",
            error = "this is error"
        )
    }
}

