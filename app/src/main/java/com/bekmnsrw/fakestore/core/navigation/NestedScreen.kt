package com.bekmnsrw.fakestore.core.navigation

sealed class NestedScreen(
    val route: String,
    val reusableRoute: String
) {

    object ProductDetails : NestedScreen(
        route = "$DETAILS_SCREEN_ROUTE_FROM_MAIN_SCREEN/{productId}",
        reusableRoute = "$DETAILS_SCREEN_ROUTE_FROM_PRODUCTS_OF_CATEGORY_SCREEN/{productId}"
    ) {

        fun navigateFromMainScreen(
            productId: Long
        ): String = "$DETAILS_SCREEN_ROUTE_FROM_MAIN_SCREEN/$productId"

        fun navigateFromProductsOfCategoryScreen(
            productId: Long
        ): String = "$DETAILS_SCREEN_ROUTE_FROM_PRODUCTS_OF_CATEGORY_SCREEN/$productId"
    }

    object ProductsOfCategory : NestedScreen(
        route = "$PRODUCTS_OF_CATEGORY_SCREEN_ROUTE_FROM_CATEGORY_SCREEN/{category}",
        reusableRoute = "$PRODUCTS_OF_CATEGORY_SCREEN_ROUTE_FROM_MAIN_SCREEN/{category}"
    ) {

        fun navigateFromCategoryScreen(
            category: String
        ): String = "$PRODUCTS_OF_CATEGORY_SCREEN_ROUTE_FROM_CATEGORY_SCREEN/$category"

        fun navigateFromMainScreen(
            category: String
        ): String = "$PRODUCTS_OF_CATEGORY_SCREEN_ROUTE_FROM_MAIN_SCREEN/$category"
    }
}
