package com.bekmnsrw.fakestore.core.navigation

sealed class NavigationGraph(
    val startDestination: String,
    val route: String
) {

    object MainGraph : NavigationGraph(
        startDestination = BottomAppBarItem.Main.route,
        route = "main_graph"
    )

    object CatalogGraph : NavigationGraph(
        startDestination = BottomAppBarItem.Catalog.route,
        route = "catalog_graph"
    )

    object CartGraph : NavigationGraph(
        startDestination = BottomAppBarItem.Cart.route,
        route = "cart_graph"
    )

    object FavoritesGraph : NavigationGraph(
        startDestination = BottomAppBarItem.Favorites.route,
        route = "favorites_graph"
    )

    object ProfileGraph : NavigationGraph(
        startDestination = BottomAppBarItem.Profile.route,
        route = "profile_graph"
    )
}
