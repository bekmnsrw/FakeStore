package com.bekmnsrw.fakestore.core.navigation.graph

import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bekmnsrw.fakestore.core.navigation.CATALOG_SCREEN_ROUTE
import com.bekmnsrw.fakestore.core.navigation.NestedScreen
import com.bekmnsrw.fakestore.feature.main.presentation.details.DetailsScreen
import com.bekmnsrw.fakestore.feature.search.presentation.CategoryScreen
import com.bekmnsrw.fakestore.feature.search.presentation.ProductsOfCategoryScreen

fun NavGraphBuilder.catalogNavGraph(
    navController: NavController,
    viewModelStoreOwner: ViewModelStoreOwner
) {

    navigation(
        startDestination = CATALOG_SCREEN_ROUTE,
        route = NavigationGraph.CatalogGraph.route
    ) {
        composable(route = CATALOG_SCREEN_ROUTE) {
            CategoryScreen(
                navController = navController,
                viewModel = viewModel(viewModelStoreOwner)
            )
        }

        composable(route = NestedScreen.ProductsOfCategoryList.route) {
            ProductsOfCategoryScreen(navController = navController)
        }

        composable(route = NestedScreen.ProductDetails.reusableRoute) {
            DetailsScreen(navController = navController)
        }
    }
}
