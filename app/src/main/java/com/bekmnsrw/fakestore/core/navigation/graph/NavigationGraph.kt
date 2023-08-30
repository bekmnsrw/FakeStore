package com.bekmnsrw.fakestore.core.navigation.graph

import com.bekmnsrw.fakestore.core.navigation.CART_GRAPH_ROUTE
import com.bekmnsrw.fakestore.core.navigation.CATALOG_GRAPH_ROUTE
import com.bekmnsrw.fakestore.core.navigation.FAVORITES_GRAPH_ROUTE
import com.bekmnsrw.fakestore.core.navigation.MAIN_GRAPH_ROUTE
import com.bekmnsrw.fakestore.core.navigation.PROFILE_GRAPH_ROUTE

sealed class NavigationGraph(val route: String) {

    object MainGraph : NavigationGraph(route = MAIN_GRAPH_ROUTE)
    object CatalogGraph : NavigationGraph(route = CATALOG_GRAPH_ROUTE)
    object CartGraph : NavigationGraph(route = CART_GRAPH_ROUTE)
    object FavoritesGraph : NavigationGraph(route = FAVORITES_GRAPH_ROUTE)
    object ProfileGraph : NavigationGraph(route = PROFILE_GRAPH_ROUTE)
}
