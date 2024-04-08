package com.jerryalberto.mmas.feature.home.ui.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jerryalberto.mmas.core.ui.constants.ColorConstant


@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: List<Pair<Color, Double>> = listOf(),
    radiusOuter: Dp = 100.dp,
    chartBarWidth: Dp = 35.dp,
    animDuration: Int = 1000,
) {
    val totalSum = data.sumOf { it.second }
    val floatValue = mutableListOf<Float>()


    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // For a detailed explanation check out the Medium Article.
    // The link is in the about section and readme file of this GitHub Repository
    data.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.second.toFloat() / totalSum.toFloat() )
    }

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // 90f is used to complete 1/4 of the rotation
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = Unit) {
        animationPlayed = true
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
//            .requiredSize(chartSize)
//            .aspectRatio(1f)
//            .clipToBounds()
//            .background(Color.Transparent)
        ,
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .size(radiusOuter * 2f)
                .rotate(animateRotation)
        ) {
            // draw each Arc for each data entry in Pie Chart
            floatValue.forEachIndexed { index, value ->
                drawArc(
                    color = data[index].first,
                    lastValue,
                    value,
                    useCenter = false,
                    style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                )
                lastValue += value
            }
        }
    }
    
}

@Preview(showBackground = false)
@Composable
private fun PieChartPreview(){
    PieChart(
        data = listOf(
            Pair(ColorConstant.ExpensesRed, 150.0),
            Pair(ColorConstant.IncomeGreen,0.0),
        )
    )
}
