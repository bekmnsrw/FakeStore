package com.bekmnsrw.fakestore.feature.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsOfCategoryScreen(
    navController: NavController,
    viewModel: ProductsOfCategoryViewModel = hiltViewModel()
) {

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)
    val pagedProducts = viewModel.pagedProducts.collectAsLazyPagingItems()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )

    ProductsOfCategoryContent(
        eventHandler = viewModel::eventHandler,
        pagedProducts = pagedProducts,
        scrollBehavior = scrollBehavior
    )

    ProductsOfCategoryActions(
        navController = navController,
        screenAction = screenAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsOfCategoryContent(
    eventHandler: (ProductsOfCategoryViewModel.ProductsOfCategoryScreenEvent) -> Unit,
    pagedProducts: LazyPagingItems<ProductMain>,
    scrollBehavior: TopAppBarScrollBehavior
) {

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ProductsOfCategoryScreenTopBar(
                scrollBehavior = scrollBehavior,
                eventHandler = eventHandler
            )
        }
    ) { contentPadding ->

        ProductsOfCategoryList(
            eventHandler = eventHandler,
            pagedProducts = pagedProducts,
            paddingValues = contentPadding
        )
    }

    if (pagedProducts.loadState.refresh == LoadState.Loading) CircularProgressBar(shouldShow = true)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsOfCategoryScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    eventHandler: (ProductsOfCategoryViewModel.ProductsOfCategoryScreenEvent) -> Unit
) = SmallTopAppBar(
    title = {},
    navigationIcon = {
        IconButton(
            onClick = {
                eventHandler(
                    ProductsOfCategoryViewModel.ProductsOfCategoryScreenEvent.OnArrowBackClicked
                )
            }
        ) {

            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null
            )
        }
    },
    scrollBehavior = scrollBehavior
)

@Composable
fun ProductsOfCategoryList(
    eventHandler: (ProductsOfCategoryViewModel.ProductsOfCategoryScreenEvent) -> Unit,
    pagedProducts: LazyPagingItems<ProductMain>,
    paddingValues: PaddingValues
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.background)
            .padding(paddingValues)
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
                        ProductsOfCategoryViewModel.ProductsOfCategoryScreenEvent.OnProductClicked(it)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductsOfCategoryActions(
    navController: NavController,
    screenAction: ProductsOfCategoryViewModel.ProductsOfCategoryScreenAction?
) {

    LaunchedEffect(screenAction) {
        when (screenAction) {
            null -> Unit

            is ProductsOfCategoryViewModel.ProductsOfCategoryScreenAction.NavigateProductDetails -> navController.navigate(
                NestedScreen.ProductDetails.navigateFromProductsOfCategoryScreen(
                    productId = screenAction.id
                )
            )

            ProductsOfCategoryViewModel.ProductsOfCategoryScreenAction.NavigateBack -> navController.navigateUp()
        }
    }
}
