package com.bekmnsrw.fakestore.core.navigation

import android.os.Build
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.bekmnsrw.fakestore.core.permission.RequestNotificationPermissionDialog
import com.bekmnsrw.fakestore.feature.main.presentation.details.DetailsScreen
import com.bekmnsrw.fakestore.feature.main.presentation.list.MainScreen
import com.bekmnsrw.fakestore.ui.theme.CustomTheme

@Composable
fun NavigationHost(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = NavigationGraph.MainGraph.route
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestNotificationPermissionDialog()
    }

    val bottomAppBarItems = listOf(
        BottomAppBarItem.Main,
        BottomAppBarItem.Catalog,
        BottomAppBarItem.Cart,
        BottomAppBarItem.Favorites,
        BottomAppBarItem.Profile
    )

    Scaffold(
        bottomBar = {
            CustomBottomAppBar(
                navHostController = navHostController,
                bottomAppBarItems = bottomAppBarItems
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {

            navigation(
                startDestination = NavigationGraph.MainGraph.startDestination,
                route = NavigationGraph.MainGraph.route
            ) {
                composable(route = BottomAppBarItem.Main.route) { MainScreen(navController = navHostController) }
                composable(route = NestedScreen.ProductDetails.route) { DetailsScreen(navController = navHostController) }
            }

            navigation(
                startDestination = NavigationGraph.CatalogGraph.startDestination,
                route = NavigationGraph.CatalogGraph.route
            ) {
                composable(route = BottomAppBarItem.Catalog.route) {  }
            }

            navigation(
                startDestination = NavigationGraph.CartGraph.startDestination,
                route = NavigationGraph.CartGraph.route
            ) {
                composable(route = BottomAppBarItem.Cart.route) {  }
            }

            navigation(
                startDestination = NavigationGraph.FavoritesGraph.startDestination,
                route = NavigationGraph.FavoritesGraph.route
            ) {
                composable(route = BottomAppBarItem.Favorites.route) {  }
            }

            navigation(
                startDestination = NavigationGraph.ProfileGraph.startDestination,
                route = NavigationGraph.ProfileGraph.route
            ) {
                composable(route = BottomAppBarItem.Profile.route) {  }
            }
        }
    }
}

@Composable
fun CustomBottomAppBar(
    navHostController: NavHostController,
    bottomAppBarItems: List<BottomAppBarItem>
) {

    BottomNavigation(
        backgroundColor = CustomTheme.colors.bottomAppBarBackground
    ) {

        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
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
                    navHostController.navigate(item.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) { saveState = true }
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
