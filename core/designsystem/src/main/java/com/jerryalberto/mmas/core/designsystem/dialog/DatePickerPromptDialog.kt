package com.jerryalberto.mmas.core.designsystem.dialog

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerPromptDialog(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean =
            utcTimeMillis <= System.currentTimeMillis()
    })

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                datePickerState.selectedDateMillis?.let{
                    onDateSelected.invoke(it)
                }
                onDismiss()
            }

            ) {
                Text(text = stringResource(R.string.core_designsystem_ok))
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(R.string.core_designsystem_cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0000F)
@Composable
private fun DatePickerPromptDialogPreview() {
    DatePickerPromptDialog(
        onDateSelected = {},
        onDismiss = {}
    )
}