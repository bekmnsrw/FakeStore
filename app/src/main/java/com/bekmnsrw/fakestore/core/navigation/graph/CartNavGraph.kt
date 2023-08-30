package com.bekmnsrw.fakestore.core.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bekmnsrw.fakestore.core.navigation.CART_SCREEN_ROUTE

fun NavGraphBuilder.cartNavGraph(navController: NavController) {

    navigation(
        startDestination = CART_SCREEN_ROUTE,
        route = NavigationGraph.CartGraph.route
    ) {
        composable(route = CART_SCREEN_ROUTE) {}
    }
}
