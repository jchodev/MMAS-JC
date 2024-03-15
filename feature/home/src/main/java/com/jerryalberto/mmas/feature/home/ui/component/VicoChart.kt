package com.jerryalberto.mmas.feature.home.ui.component

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun VicoChart() {
    val chartEntryModel = entryModelOf(4f, 12f, 8f, 16f, 4f, 12f, 8f, 16f, 4f, 12f, 8f, 16f, 4f, 12f, 8f, 16f, 4f, 12f, 8f, 16f)

    Chart(
        modifier = Modifier.wrapContentHeight(),
        chart = lineChart(
            lines = listOf (
                lineSpec(
                    lineColor = Color.Black
                )
            )
        ),
        model = chartEntryModel,
        startAxis = rememberStartAxis(

            title = "This is start Axis",

            label = textComponent {
                color = Color.Black.toArgb()
                textSizeSp = 12f
            },

            axis = LineComponent(
                color = Color.Black.toArgb()
            ),

            tick = LineComponent(
                color = Color.Black.toArgb()
            ),

            valueFormatter = { value, _ ->
                value.toInt().toString()
            },
        ),
        bottomAxis = rememberBottomAxis(
            title = "This is bottom Axis",

            label = textComponent {
                color = Color.Black.toArgb()
                textSizeSp = 12f
            },

            axis = LineComponent(
                color = Color.Black.toArgb()
            ),

            tick = LineComponent(
                color = Color.Black.toArgb()
            ),

            valueFormatter = { value, _ ->
                (value.toInt() + 1).toString()
            },
        ),
    )
}

@Preview(showBackground = false)
@Composable
private fun MonitorScreenContent() {
    MmasTheme {
        VicoChart()
    }

}