package com.bekmnsrw.fakestore.feature.main.presentation.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bekmnsrw.fakestore.core.database.CATEGORY_MOCK_DATA
import com.bekmnsrw.fakestore.core.navigation.NestedScreen
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import com.bekmnsrw.fakestore.ui.theme.CustomTheme

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)
    val pagedProducts = viewModel.pagedProducts.collectAsLazyPagingItems()

    MainContent(
        eventHandler = viewModel::eventHandler,
        pagedProducts = pagedProducts,
        screenState = screenState.value
    )

    MainActions(
        screenAction = screenAction,
        navController = navController
    )
}

@Composable
fun MainContent(
    eventHandler: (MainViewModel.MainScreenEvent) -> Unit,
    pagedProducts: LazyPagingItems<ProductMain>,
    screenState: MainViewModel.MainScreenState
) {

    ProductMainList(
        eventHandler = eventHandler,
        pagedProducts = pagedProducts,
        screenState = screenState
    )

    if (pagedProducts.loadState.refresh == LoadState.Loading) CircularProgressBar(shouldShow = true)
}

@Composable
fun ProductMainList(
    eventHandler: (MainViewModel.MainScreenEvent) -> Unit,
    pagedProducts: LazyPagingItems<ProductMain>,
    screenState: MainViewModel.MainScreenState
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

        item(span = { GridItemSpan(2) }) {
            CategoryList(
                categories = screenState.categories
            )
        }

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
                        MainViewModel.MainScreenEvent.OnProductClicked(product.id)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryList(
    categories: List<String>
) {

    val density = LocalDensity.current
    val offsetPx = with (density) { 16.dp.roundToPx() }

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
            .layout { measurable, constraints ->
                val looseConstraints = constraints.offset(offsetPx * 2, 0)
                val placeable = measurable.measure(looseConstraints)
                layout(placeable.width, placeable.height) { placeable.placeRelative(0, 0) }
            }
            .fillMaxWidth()
    ) {

        items(
            items = categories,
            key = { it }
        ) {

            CategoryListItem(
                category = it,
                onClick = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListItem(
    category: String,
    onClick: (String) -> Unit
) {

    Card(
        modifier = Modifier.size(
            height = 96.dp,
            width = 72.dp
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick(category) },
        colors = CardDefaults.cardColors(
            containerColor = CustomTheme.colors.background
        )
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = CATEGORY_MOCK_DATA[category]?.second)
                    .crossfade(enable = true)
                    .diskCachePolicy(policy = CachePolicy.ENABLED)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(
                text = CATEGORY_MOCK_DATA[category]?.first.toString(),
                style = CustomTheme.typography.categoryCardMainScreen,
                color = CustomTheme.colors.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListItem(
    productMain: ProductMain,
    onClick: (Long) -> Unit
) {

    Card(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(size = 16.dp),
        onClick = { onClick(productMain.id) },
        colors = CardDefaults.cardColors(
            containerColor = CustomTheme.colors.cardBackground
        )
    ) {

        Column {

            ProductImage(
                imageUrl = productMain.thumbnail,
                isFavorite = false
            )

            Column(
                modifier = Modifier.padding(
                    vertical = 12.dp,
                    horizontal = 8.dp
                )
            ) {

                ProductTitle(
                    title = productMain.title
                )

                ProductRating(
                    rate = productMain.rating,
                    count = productMain.stock
                )

                ProductPrice(
                    fullPrice = productMain.price,
                    discountPrice = productMain.discountPrice
                )
            }
        }
    }
}

@Composable
fun ProductImage(
    imageUrl: String,
    isFavorite: Boolean
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(size = 16.dp))
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = imageUrl)
                .crossfade(enable = true)
                .diskCachePolicy(policy = CachePolicy.ENABLED)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Icon(
            imageVector = when (isFavorite) {
                true -> Icons.Outlined.Favorite
                false -> Icons.TwoTone.Favorite
            },
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 12.dp,
                    end = 12.dp
                ),
            tint = when (isFavorite) {
                true -> CustomTheme.colors.isFavoriteSelected
                false -> CustomTheme.colors.isFavoriteUnselected
            }
        )
    }
}

@Composable
fun ProductTitle(
    title: String
) {

    Text(
        text = title,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
        style = CustomTheme.typography.cardTitle,
        color = CustomTheme.colors.cardMainText
    )
}

@Composable
fun ProductRating(
    rate: Double,
    count: Long
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .offset(x = (-2).dp)
    ) {

        Icon(
            imageVector = Icons.Outlined.Star,
            contentDescription = null,
            tint = CustomTheme.colors.rate
        )

        Text(
            text = " $rate ($count orders)",
            style = CustomTheme.typography.cardRating,
            color = CustomTheme.colors.cardSupportingText
        )
    }
}

@Composable
private fun ProductPrice(
    fullPrice: Double,
    discountPrice: Double
) {

    val hasDiscount = discountPrice != 0.0

    Column {

        Text(
            text = when (hasDiscount) {
                true -> "$fullPrice $"
                false -> ""
            },
            style = CustomTheme.typography.cardFullPrice,
            color = CustomTheme.colors.cardSupportingText
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = when (hasDiscount) {
                    true -> "$discountPrice $"
                    false -> "$fullPrice $"
                },
                style = CustomTheme.typography.cardDiscountPrice,
                color = CustomTheme.colors.cardMainText
            )

            AddShoppingCartButton()
        }
    }
}

@Composable
fun CircularProgressBar(
    shouldShow: Boolean
) {

    if (shouldShow) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            CircularProgressIndicator(
                color = CustomTheme.colors.progressBar
            )
        }
    }
}

@Composable
fun AddShoppingCartButton() {

    OutlinedButton(
        onClick = {},
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = CustomTheme.colors.bottomAppBarItemUnselected
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(40.dp)
    ) {

        Icon(
            imageVector = Icons.Rounded.AddShoppingCart,
            contentDescription = null,
            tint = CustomTheme.colors.bottomAppBarItemSelected
        )
    }
}

@Composable
fun MainActions(
    screenAction: MainViewModel.MainScreenAction?,
    navController: NavController
) {

    LaunchedEffect(screenAction) {
        when (screenAction) {
            null -> Unit
            is MainViewModel.MainScreenAction.NavigateProductDetails -> navController.navigate(
                NestedScreen.ProductDetails.navigateFromMainScreen(
                    productId = screenAction.id
                )
            )
        }
    }
}
