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

    object ProductsOfCategoryList : NestedScreen(
        route = "$PRODUCTS_OF_CATEGORY_ROUTE/{category}",
        reusableRoute = ""
    ) {

        fun createRoute(
            category: String
        ): String = "$PRODUCTS_OF_CATEGORY_ROUTE/$category"
    }
}
