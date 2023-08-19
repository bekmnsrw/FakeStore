package com.bekmnsrw.fakestore.feature.main.presentation

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import com.bekmnsrw.fakestore.ui.theme.CustomTheme

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)

    MainContent(
        screenState = screenState.value
    )

    MainActions(

    )
}

@Composable
fun MainContent(
    screenState: MainViewModel.MainScreenState
) {
    ProductList(
        screenState = screenState
    )

    CircularProgressBar(
        shouldShow = screenState.isLoading
    )
}

@Composable
fun ProductList(
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
        items(
            items = screenState.products,
            key = { it.id }
        ) {
            ProductListItem(
                productMain = it
            ) {

            }
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
                imageUrl = productMain.image,
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
                    rate = productMain.rate,
                    count = productMain.count
                )

                ProductPrice(
                    fullPrice = productMain.fullPrice,
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
                .data(imageUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
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
fun ProductPrice(
    fullPrice: Double,
    discountPrice: Double
) {

    val hasDiscount = discountPrice != 0.0

    Column {

        Text(
            text = when (hasDiscount) {
                true -> "$fullPrice ₽"
                false -> ""
            },
            style = CustomTheme.typography.cardDiscountPrice,
            color = CustomTheme.colors.cardSupportingText
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = when (hasDiscount) {
                    true -> "$discountPrice ₽"
                    false -> "$fullPrice ₽"
                },
                style = CustomTheme.typography.cardFullPrice,
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
        modifier= Modifier.size(40.dp)
    ) {

        Icon(
            imageVector = Icons.Rounded.AddShoppingCart,
            contentDescription = null,
            tint = CustomTheme.colors.bottomAppBarItemSelected
        )
    }
}

@Composable
fun MainActions() {
}
