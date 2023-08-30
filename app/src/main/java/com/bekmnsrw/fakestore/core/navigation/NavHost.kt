package com.bekmnsrw.fakestore.core.navigation

import android.os.Build
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bekmnsrw.fakestore.core.navigation.bottombar.BottomAppBarItem
import com.bekmnsrw.fakestore.core.navigation.bottombar.BottomNavBar
import com.bekmnsrw.fakestore.core.navigation.graph.cartNavGraph
import com.bekmnsrw.fakestore.core.navigation.graph.catalogNavGraph
import com.bekmnsrw.fakestore.core.navigation.graph.favoritesNavGraph
import com.bekmnsrw.fakestore.core.navigation.graph.mainNavGraph
import com.bekmnsrw.fakestore.core.navigation.graph.profileNavGraph
import com.bekmnsrw.fakestore.core.permission.RequestNotificationPermissionDialog

@Composable
fun NavHost(
    navController: NavHostController = rememberNavController()
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestNotificationPermissionDialog()
    }

    val bottomAppBarItems = listOf(
        BottomAppBarItem.Main,
        BottomAppBarItem.Catalog,
        BottomAppBarItem.Cart,
        BottomAppBarItem.Favorites,
        BottomAppBarItem.Profile
    )

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                bottomAppBarItems = bottomAppBarItems
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MAIN_GRAPH_ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {

            mainNavGraph(
                navController = navController
            )

            catalogNavGraph(
                navController = navController,
                viewModelStoreOwner = viewModelStoreOwner
            )

            cartNavGraph(
                navController = navController
            )

            favoritesNavGraph(
                navController = navController
            )

            profileNavGraph(
                navController = navController
            )
        }
    }
}
