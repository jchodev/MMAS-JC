package com.jerryalberto.mmas.feature.calendar.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerryalberto.mmas.core.designsystem.dialog.DatePickerPromptDialog
import com.jerryalberto.mmas.feature.calendar.R

@Composable
fun CalendarScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "This is Calendar screen",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
        MmasDatePickerDialog()
    }
}

@Composable
private fun MmasDatePickerDialog() {
    var date by remember {
        mutableStateOf("dd/MM/yyyy")
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Button(onClick = { showDatePicker = true }) {
        Text(text = stringResource(R.string.open_date_picker_dialog), textAlign = TextAlign.Center)
    }
    Spacer(modifier = Modifier.height(48.dp))
    Text(text = date, textAlign = TextAlign.Center)

    if (showDatePicker) {
        DatePickerPromptDialog(
            dateFormat = stringResource(R.string.dd_mm_yyyy),
            onDateSelected = { date = it },
            onDismiss = { showDatePicker = false }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0000F)
@Composable
private fun CalendarScreenPreview() {
    CalendarScreen()
}
