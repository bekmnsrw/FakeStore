package com.bekmnsrw.fakestore.feature.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.bekmnsrw.fakestore.core.navigation.NestedScreen
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import com.bekmnsrw.fakestore.feature.main.presentation.list.CircularProgressBar
import com.bekmnsrw.fakestore.feature.main.presentation.list.ProductListItem
import com.bekmnsrw.fakestore.ui.theme.CustomTheme

@Composable
fun ProductsOfCategoryScreen(
    navController: NavController,
    viewModel: ProductsOfCategoryListViewModel = hiltViewModel()
) {

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)
    val pagedProducts = viewModel.pagedProducts.collectAsLazyPagingItems()

    ProductsOfCategoryContent(
        eventHandler = viewModel::eventHandler,
        pagedProducts = pagedProducts
    )

    ProductsOfCategoryActions(
        navController = navController,
        screenAction = screenAction
    )
}

@Composable
fun ProductsOfCategoryContent(
    eventHandler: (ProductsOfCategoryListViewModel.ProductsOfCategoryScreenEvent) -> Unit,
    pagedProducts: LazyPagingItems<ProductMain>
) {

    ProductsOfCategoryList(
        eventHandler = eventHandler,
        pagedProducts = pagedProducts
    )

    if (pagedProducts.loadState.refresh == LoadState.Loading) CircularProgressBar(shouldShow = true)
}

@Composable
fun ProductsOfCategoryList(
    eventHandler: (ProductsOfCategoryListViewModel.ProductsOfCategoryScreenEvent) -> Unit,
    pagedProducts: LazyPagingItems<ProductMain>
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.background)
    ) {

        items(
            count = pagedProducts.itemCount,
            key = pagedProducts.itemKey { it.id },
            contentType = pagedProducts.itemContentType { "Products" }
        ) { index ->
            pagedProducts[index]?.let { product ->
                ProductListItem(
                    productMain = product
                ) {
                    eventHandler(
                        ProductsOfCategoryListViewModel.ProductsOfCategoryScreenEvent.OnProductClicked(it)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductsOfCategoryActions(
    navController: NavController,
    screenAction: ProductsOfCategoryListViewModel.ProductsOfCategoryScreenAction?
) {

    LaunchedEffect(screenAction) {
        when (screenAction) {
            null -> Unit
            is ProductsOfCategoryListViewModel.ProductsOfCategoryScreenAction.NavigateProductDetails -> navController.navigate(
                NestedScreen.ProductDetails.navigateFromProductsOfCategoryScreen(productId = screenAction.id)
            )
        }
    }
}
