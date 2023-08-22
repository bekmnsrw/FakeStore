package com.bekmnsrw.fakestore.core.navigation

sealed class NestedScreen(
    val route: String
) {

    object ProductDetails : NestedScreen(
        route = "details/{productId}"
    ) {

        fun createRoute(
            productId: Long
        ): String = "details/$productId"
    }
}
