package com.jerryalberto.mmas.core.designsystem.textfield

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.jerryalberto.mmas.core.designsystem.R

@Composable
fun TopBarSearchTextField(
    searchValue: String = "",
    onValueChange: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    TextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = searchValue,

        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = stringResource(id = R.string.core_designsystem_search),
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = MaterialTheme.typography.labelLarge,
    )
}