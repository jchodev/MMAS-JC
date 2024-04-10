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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jerryalberto.mmas.core.designsystem.R
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerPromptDialog(
    defaultHour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    defaultMin: Int = Calendar.getInstance().get(Calendar.MINUTE),
    title: String = "Select Time",
    onSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit ={}
) {
    val timePickerState = rememberTimePickerState(
        initialHour = defaultHour,
        initialMinute = defaultMin
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = MaterialTheme.dimens.dimen8,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(MaterialTheme.dimens.dimen24),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MaterialTheme.dimens.dimen24),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                TimePicker(
                    state = timePickerState
                )
                Row(
                    modifier = Modifier
                        .height(MaterialTheme.dimens.dimen40)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        modifier = Modifier.padding(end = MaterialTheme.dimens.dimen8),
                        onClick = {
                        onDismiss()
                    }) {
                        Text(text = stringResource(R.string.core_designsystem_cancel))
                    }

                    Button(onClick = {
                        onSelected(
                            timePickerState.hour,
                            timePickerState.minute
                        )
                        onDismiss()
                    }

                    ) {
                        Text(text = stringResource(R.string.core_designsystem_ok))
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