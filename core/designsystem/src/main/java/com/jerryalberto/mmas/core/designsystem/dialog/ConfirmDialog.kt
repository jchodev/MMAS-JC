package com.jerryalberto.mmas.core.designsystem.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jerryalberto.mmas.core.designsystem.R
import com.jerryalberto.mmas.core.designsystem.theme.dimens

@Composable
fun ConfirmDialog(
    title: String? = "this is title",
    text: String = "this is text",
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    AlertDialog(
        onDismissRequest =onDismissRequest,
        title = {
            title?.let{
                Text(text = title)
            }
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        },
        confirmButton = {
            Button(
                modifier = Modifier.padding(end = MaterialTheme.dimens.dimen8),
                onClick =onConfirmRequest
            ) {
                Text(text = stringResource(R.string.core_designsystem_confirm))
            }
        },
        dismissButton = {
            Button(
                modifier = Modifier.padding(end = MaterialTheme.dimens.dimen8),
                onClick =onDismissRequest
            ) {
                Text(text = stringResource(R.string.core_designsystem_cancel))
            }
        }
    )
}