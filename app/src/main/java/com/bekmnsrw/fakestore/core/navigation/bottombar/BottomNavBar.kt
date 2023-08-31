package com.bekmnsrw.fakestore.core.navigation.bottombar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.bekmnsrw.fakestore.ui.theme.CustomTheme

@Composable
fun BottomNavBar(
    navController: NavController,
    bottomAppBarItems: List<BottomAppBarItem>
) {

    BottomNavigation(
        backgroundColor = CustomTheme.colors.bottomAppBarBackground
    ) {

        val startDestination = BottomAppBarItem.Main
        var selectedTab by remember { mutableIntStateOf(bottomAppBarItems.indexOf(startDestination)) }

        bottomAppBarItems.forEachIndexed { index, item ->
            val isSelected = index == selectedTab

            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    selectedTab = index
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = when (isSelected) {
                            true -> CustomTheme.colors.bottomAppBarItemSelected
                            false -> CustomTheme.colors.bottomAppBarItemUnselected
                        }
                    )
                }
            )
        }
    }
}
