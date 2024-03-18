package com.jerryalberto.mmas.core.designsystem.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.TimePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jerryalberto.mmas.core.designsystem.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerPromptDialog(
    title: String = "Select Time",
    onSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit ={}
) {
    val timePickerState = rememberTimePickerState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                TimePicker(
                    state = timePickerState
                )
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                        onDismiss()
                    }) {
                        Text(text = stringResource(R.string.cancel))
                    }

                    Button(onClick = {
                        onSelected(
                            timePickerState.hour,
                            timePickerState.minute
                        )
                        onDismiss()
                    }

                    ) {
                        Text(text = stringResource(R.string.ok))
                    }


                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF0000F)
@Composable
private fun TimePickerPromptDialogPreview() {

    TimePickerPromptDialog(
        onSelected = {
                hour, minute ->
            // Simulate action on selection (optional)
            //Log.d("TimePicker", "Selected time: $hour:$minute")
        },
        onDismiss = {}
    )
}