package com.jerryalberto.mmas.core.designsystem.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(
    onDateSelected: (dateSelected: String) -> Unit
) {
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd MM yyyy")
                .format(pickedDate)
        }
    }
    val dateDialogState = rememberMaterialDialogState()

    Button(onClick = {
        dateDialogState.show()
    }) {
        Text(text = "Show calendar")
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("OK") {
                onDateSelected(formattedDate)
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now()
        ) {
            pickedDate = it
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xFF0000F)
@Composable
private fun DatePickerPreview() {
    DatePicker(
        onDateSelected = { }
    )
}