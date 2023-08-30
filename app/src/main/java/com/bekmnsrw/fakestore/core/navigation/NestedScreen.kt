package com.bekmnsrw.fakestore.core.navigation

sealed class NestedScreen(
    val route: String,
    val reusableRoute: String
) {

    object ProductDetails : NestedScreen(
        route = "$DETAILS_SCREEN_ROUTE/{productId}",
        reusableRoute = "$DETAILS_SCREEN_REUSABLE_ROUTE/{productId}"
    ) {

        fun navigateFromMainScreen(
            productId: Long
        ): String = "$DETAILS_SCREEN_ROUTE/$productId"

        fun navigateFromProductsOfCategoryScreen(
            productId: Long
        ): String = "$DETAILS_SCREEN_REUSABLE_ROUTE/$productId"
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
