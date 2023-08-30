package com.bekmnsrw.fakestore.core.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bekmnsrw.fakestore.core.navigation.FAVORITES_SCREEN_ROUTE

fun NavGraphBuilder.favoritesNavGraph(navController: NavController) {

    navigation(
        startDestination = FAVORITES_SCREEN_ROUTE,
        route = NavigationGraph.FavoritesGraph.route
    ) {
        composable(route = FAVORITES_SCREEN_ROUTE) {}
    }
}
