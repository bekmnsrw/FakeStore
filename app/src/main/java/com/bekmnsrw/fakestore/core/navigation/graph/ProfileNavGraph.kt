package com.bekmnsrw.fakestore.core.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bekmnsrw.fakestore.core.navigation.PROFILE_SCREEN_ROUTE

fun NavGraphBuilder.profileNavGraph(navController: NavController) {

    navigation(
        startDestination = PROFILE_SCREEN_ROUTE,
        route = NavigationGraph.ProfileGraph.route
    ) {
        composable(route = PROFILE_SCREEN_ROUTE) {}
    }
}
