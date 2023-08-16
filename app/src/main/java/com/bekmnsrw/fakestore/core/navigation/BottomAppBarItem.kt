package com.bekmnsrw.fakestore.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomAppBarItem(
    val route: String,
    val icon: ImageVector
) {

    object Main : BottomAppBarItem(
        route = "main",
        icon = Icons.Outlined.Home
    )

    object Catalog : BottomAppBarItem(
        route = "catalog",
        icon = Icons.Outlined.Search
    )

    object Cart : BottomAppBarItem(
        route = "cart",
        icon = Icons.Outlined.ShoppingCart
    )

    object Favorites : BottomAppBarItem(
        route = "favorites",
        icon = Icons.Outlined.FavoriteBorder
    )

    object Profile : BottomAppBarItem(
        route = "profile",
        icon = Icons.Outlined.Person
    )
}
