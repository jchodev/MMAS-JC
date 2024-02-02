package com.jerryalberto.mmas.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundBoxWithTwoColumn(
    //line1
    title1: String = "",
    value1: String = "",

    //line2
    title2: String = "",
    value2: String = "",
) {



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(32.dp)) // Top and side borders only
                .background(
                    //color = MaterialTheme.colorScheme.surface,
                    color  = Color.Green, // for testing
                    shape = RoundedCornerShape(32.dp)
                )
                //.background(color = Color.Green) // for testing
        ) {
            // Content for the first row
            Row (
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
            ) {

                Text(
                    text = title1,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(weight = 1f))

                Text(
                    text = value1,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )
            }

            Row (
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            ) {

                Text(
                    text = title2,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(weight = 1f))

                Text(
                    text = value2,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }



}


@Preview(showBackground = true)
@Composable
private fun RoundBoxWithTwoColumnPreview(){
    RoundBoxWithTwoColumn(
        title1 = "title1",
        value1 = "$100",
        title2 = "title2",
        value2 = "$101"
    )
}