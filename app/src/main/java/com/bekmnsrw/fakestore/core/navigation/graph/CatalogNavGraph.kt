package com.bekmnsrw.fakestore.core.navigation.graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
        composable(
            route = CATALOG_SCREEN_ROUTE,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            CategoryScreen(
                navController = navController,
                viewModel = viewModel(viewModelStoreOwner)
            )
        }

        composable(route = NestedScreen.ProductsOfCategory.route) {
            ProductsOfCategoryScreen(navController = navController)
        }

        composable(
            route = NestedScreen.ProductDetails.reusableRoute,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = EaseIn
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = EaseOut
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            DetailsScreen(navController = navController)
        }
    }
}
