package com.jerryalberto.mmas.feature.home.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButtonBar(
    tabTitles: List<String>,
    defaultTab: Int = 0,
    onSelected: (Int) -> Unit = {}
) {
    val selectedTabIndex = remember { mutableStateOf(defaultTab) }

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        tabTitles.forEachIndexed { index, item ->
            SegmentedButton(
                selected = selectedTabIndex.value == index,
                onClick = {
                    selectedTabIndex.value = index
                    onSelected.invoke(index)
                },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = tabTitles.size
                )
            ) {
                Text(text = item, style = MaterialTheme.typography.headlineMedium)
            }
        }
    }

}

@Preview(showBackground = false)
@Composable
private fun SegmentedButtonBarPreview() {
    val tabTitles = listOf("Tab 1", "Tab 2")
    SegmentedButtonBar(tabTitles = tabTitles)
}

@Preview(showBackground = true, backgroundColor = 0xFF0000F)
@Composable
private fun SegmentedButtonBarWithBackgroundColorPreview() {
    val tabTitles = listOf("Tab 1", "Tab 2")
    SegmentedButtonBar(tabTitles = tabTitles)
}

