package com.bekmnsrw.fakestore.core.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bekmnsrw.fakestore.core.navigation.MAIN_SCREEN_ROUTE
import com.bekmnsrw.fakestore.core.navigation.NestedScreen
import com.bekmnsrw.fakestore.feature.main.presentation.details.DetailsScreen
import com.bekmnsrw.fakestore.feature.main.presentation.list.MainScreen
import com.bekmnsrw.fakestore.feature.search.presentation.ProductsOfCategoryScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController) {

    navigation(
        startDestination = MAIN_SCREEN_ROUTE,
        route = NavigationGraph.MainGraph.route
    ) {
        composable(route = MAIN_SCREEN_ROUTE) {
            MainScreen(navController = navController)
        }

        composable(route = NestedScreen.ProductDetails.route) {
            DetailsScreen(navController = navController)
        }

        composable(route = NestedScreen.ProductsOfCategory.reusableRoute) {
            ProductsOfCategoryScreen(navController = navController)
        }
    }
}
