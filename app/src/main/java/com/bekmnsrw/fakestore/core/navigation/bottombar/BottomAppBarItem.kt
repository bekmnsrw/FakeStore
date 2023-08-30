package com.bekmnsrw.fakestore.core.navigation.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.bekmnsrw.fakestore.core.navigation.CART_GRAPH_ROUTE
import com.bekmnsrw.fakestore.core.navigation.CATALOG_GRAPH_ROUTE
import com.bekmnsrw.fakestore.core.navigation.FAVORITES_GRAPH_ROUTE
import com.bekmnsrw.fakestore.core.navigation.MAIN_GRAPH_ROUTE
import com.bekmnsrw.fakestore.core.navigation.PROFILE_GRAPH_ROUTE

sealed class BottomAppBarItem(
    val route: String,
    val icon: ImageVector
) {

    object Main : BottomAppBarItem(
        route = MAIN_GRAPH_ROUTE,
        icon = Icons.Outlined.Home
    )

    object Catalog : BottomAppBarItem(
        route = CATALOG_GRAPH_ROUTE,
        icon = Icons.Outlined.Search
    )

    object Cart : BottomAppBarItem(
        route = CART_GRAPH_ROUTE,
        icon = Icons.Outlined.ShoppingCart
    )

    object Favorites : BottomAppBarItem(
        route = FAVORITES_GRAPH_ROUTE,
        icon = Icons.Outlined.FavoriteBorder
    )

    object Profile : BottomAppBarItem(
        route = PROFILE_GRAPH_ROUTE,
        icon = Icons.Outlined.Person
    )
}
