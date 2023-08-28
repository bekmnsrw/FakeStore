package com.bekmnsrw.fakestore.core.navigation

sealed class NavigationGraph(
    val startDestination: String,
    val route: String
) {

    object MainGraph : NavigationGraph(
        startDestination = BottomAppBarItem.Main.route,
        route = MAIN_GRAPH_ROUTE
    )

    object CatalogGraph : NavigationGraph(
        startDestination = BottomAppBarItem.Catalog.route,
        route = CATALOG_GRAPH_ROUTE
    )

    object CartGraph : NavigationGraph(
        startDestination = BottomAppBarItem.Cart.route,
        route = CART_GRAPH_ROUTE
    )

    object FavoritesGraph : NavigationGraph(
        startDestination = BottomAppBarItem.Favorites.route,
        route = FAVORITES_GRAPH_ROUTE
    )

    object ProfileGraph : NavigationGraph(
        startDestination = BottomAppBarItem.Profile.route,
        route = PROFILE_GRAPH_ROUTE
    )
}
