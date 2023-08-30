package com.bekmnsrw.fakestore.core.navigation.bottombar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bekmnsrw.fakestore.core.navigation.graph.NavigationGraph
import com.bekmnsrw.fakestore.ui.theme.CustomTheme

@Composable
fun BottomNavBar(
    navController: NavController,
    bottomAppBarItems: List<BottomAppBarItem>
) {

    BottomNavigation(
        backgroundColor = CustomTheme.colors.bottomAppBarBackground
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val navigationGraphsRoutes = listOf(
            NavigationGraph.MainGraph.route,
            NavigationGraph.CatalogGraph.route,
            NavigationGraph.CartGraph.route,
            NavigationGraph.FavoritesGraph.route,
            NavigationGraph.ProfileGraph.route
        )

        val currentGraphRoute = currentDestination?.hierarchy?.firstOrNull {
            it.parent?.route in navigationGraphsRoutes
        }?.parent?.route

        bottomAppBarItems.forEach { item ->
            val isSelected = when {
                item.route == BottomAppBarItem.Main.route && currentGraphRoute == NavigationGraph.MainGraph.route -> true
                item.route == BottomAppBarItem.Catalog.route && currentGraphRoute == NavigationGraph.CatalogGraph.route -> true
                item.route == BottomAppBarItem.Cart.route && currentGraphRoute == NavigationGraph.CartGraph.route -> true
                item.route == BottomAppBarItem.Favorites.route && currentGraphRoute == NavigationGraph.FavoritesGraph.route -> true
                item.route == BottomAppBarItem.Profile.route && currentGraphRoute == NavigationGraph.ProfileGraph.route -> true
                else -> false
            }

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
