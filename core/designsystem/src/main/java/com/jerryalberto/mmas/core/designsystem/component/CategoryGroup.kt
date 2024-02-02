package com.jerryalberto.mmas.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme

@Composable
fun CategoryGroup(
    modifier: Modifier = Modifier.fillMaxWidth(),
    upperText: String,
    upperIcon: ImageVector,
    upperClick: () -> Unit = {},
    items: List< Triple<String, ImageVector, (() -> Unit)>> = emptyList()
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Green) // for testing
            .padding(8.dp)
    ) {

        //row1
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
        ) {
            // Content for the first row
            Row (
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { upperClick.invoke() },
                verticalAlignment = Alignment.CenterVertically
            ) {

                CategoryIcon(
                    size = 56.dp,
                    icon = upperIcon,
                    text = upperText
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = upperText,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onSecondary
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 24.dp)
        ) {
            LazyRow {
                items(items.size) { index ->
                    val item = items[index]
                    CategoryItem(
                        text = item.first,
                        icon = item.second,
                        onClick = item.third
                    )

                    if (index < items.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryGroupPreview(){
    MmasTheme {
        CategoryGroup(
            upperText = "this is text",
            upperIcon = Icons.Filled.Settings,
            items = listOf(
                Triple(
                    first = "this is item1",
                    second = Icons.Filled.Settings,
                    third = {}
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

