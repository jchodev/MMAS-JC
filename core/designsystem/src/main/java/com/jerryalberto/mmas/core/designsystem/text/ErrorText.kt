package com.jerryalberto.mmas.core.designsystem.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorText (
    modifier: Modifier = Modifier,
    error: String?
){
    //error
    Text(
        modifier = modifier,
        text = error ?: "",
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelLarge,
    )
}