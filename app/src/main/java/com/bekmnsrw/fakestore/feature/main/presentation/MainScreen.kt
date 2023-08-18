package com.bekmnsrw.fakestore.feature.main.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import com.bekmnsrw.fakestore.ui.theme.Theme
import kotlinx.collections.immutable.persistentListOf

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MainScreenContentPreview() {
    Theme {
        MainContent(
            screenState = MainViewModel.MainScreenState(
                products = persistentListOf(
                    ProductMain(
                        id = 1,
                        title = "AF1 CRATER NN (GS)",
                        fullPrice = 675.0,
                        discountPrice = 600.0,
                        image = "https://cdn.sneakers123.com/release/949128/nike-air-force-1-crater-flyknit-dc4831-100.jpg",
                        rate = 5.0,
                        count = 200
                    )
                )
            )
        )
    }
}

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
        modifier = Modifier.fillMaxSize()
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
        onClick = { onClick(productMain.id) }
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
            .height(200.dp)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Icon(
            imageVector = when (isFavorite) {
                true -> Icons.Outlined.Favorite
                false -> Icons.Outlined.FavoriteBorder
            },
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 16.dp,
                    end = 16.dp
                )
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
        maxLines = 2
    )
}

@Composable
fun ProductRating(
    rate: Double,
    count: Long
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.Star,
            contentDescription = null,
            tint = Color(0xFFFFC107)
        )

        Text(
            text = "$rate ($count orders)"
        )
    }
}

@Composable
fun ProductPrice(
    fullPrice: Double,
    discountPrice: Double
) {

    Column {

        Text(
            text = "$fullPrice $"
        )
    }
}

@Composable
fun MainActions() {
}
