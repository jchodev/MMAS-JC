package com.jerryalberto.mmas.feature.analysis.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jerryalberto.mmas.core.designsystem.component.CategoryGroup

@Composable
fun AnalysisScreen() {
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
            Text(text = "this is analysis screen", textAlign = TextAlign.Center)
            val context = LocalContext.current
            CategoryGroup(
                upperText = "this is text",
                upperIcon = Icons.Filled.Settings,
                upperClick = {
                    Toast.makeText(context ,"this is upper toast", Toast.LENGTH_SHORT).show()
                },
                items = listOf(
                    Triple(
                        first = "this is item1",
                        second = Icons.Filled.Settings,
                        third = {
                            Toast.makeText(context ,"this is item1 toast", Toast.LENGTH_SHORT).show()
                        }
                    ),
                    Triple(
                        first = "this is item2",
                        second = Icons.Filled.Face,
                        third = {}
                    ),
                    Triple(
                        first = "this is item4",
                        second = Icons.Filled.AccountBox,
                        third = {}
                    ),
                    Triple(
                        first = "this is item5",
                        second =Icons.Filled.Build,
                        third = {}
                    ),
                    Triple(
                        first ="this is item6",
                        second = Icons.Filled.CheckCircle,
                        third = {}
                    )
                )
            )
        }
    }
}