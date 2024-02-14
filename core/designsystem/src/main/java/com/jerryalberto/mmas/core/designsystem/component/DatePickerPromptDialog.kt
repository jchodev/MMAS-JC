package com.jerryalberto.mmas.core.designsystem.component

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
import com.jerryalberto.mmas.core.designsystem.utils.convertMillisToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerPromptDialog(
    dateFormat: String,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean =
            utcTimeMillis <= System.currentTimeMillis()
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        it.convertMillisToDate(dateFormat = dateFormat)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }

            ) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(R.string.cancel))
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
        "dd/MM/yyyy",
        onDateSelected = {},
        onDismiss = {}
    )
}