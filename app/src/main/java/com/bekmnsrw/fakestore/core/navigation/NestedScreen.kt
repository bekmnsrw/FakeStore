package com.bekmnsrw.fakestore.core.navigation

sealed class NestedScreen(
    val route: String
) {

    object ProductDetails : NestedScreen(
        route = "$DETAILS_SCREEN_ROUTE/{productId}"
    ) {

        fun createRoute(
            productId: Long
        ): String = "$DETAILS_SCREEN_ROUTE/$productId"
    }
}
