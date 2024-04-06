package com.jerryalberto.mmas.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.ui.navigation.MainRoute

data class BottomBarItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val onClick : () -> Unit = {}
)

@Composable
fun BottomBar(
    items : List<BottomBarItem>,
    currentSelectedScreen: MainRoute
){

    NavigationBar {
        items.forEachIndexed { index, item ->
            val selected = currentSelectedScreen.route == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    item.onClick.invoke()
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        imageVector = if (selected) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun BottomBarPreview() {
    MmasTheme {
        BottomBar(
            currentSelectedScreen = MainRoute.HomeScreen,
            items = listOf(
                BottomBarItem(
                    route = MainRoute.HomeScreen.route,
                    title = "Add",
                    selectedIcon = Icons.Filled.Add,
                    unselectedIcon = Icons.Outlined.Add
                ),
                BottomBarItem(
                    route = MainRoute.TransactionScreen.route,
                    title = "Transaction",
                    selectedIcon = Icons.Filled.Face,
                    unselectedIcon = Icons.Outlined.Face
                ),

                BottomBarItem(
                    route = MainRoute.SettingScreen.route,
                    title = "Setting",
                    selectedIcon = Icons.Filled.Settings,
                    unselectedIcon = Icons.Outlined.Settings
                )
            )
        )
    }
}