package com.jerryalberto.mmas.feature.calendar.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jerryalberto.mmas.core.designsystem.component.DatePicker

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier =
            Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = "this is Calendar screen", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(48.dp))

            var date by remember { mutableStateOf("This is the date selected") }
            DatePicker(
                onDateSelected = { dateSelected ->
                    date = dateSelected
                }
            )
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = date, textAlign = TextAlign.Center)
        }
    }
}