package com.bekmnsrw.fakestore.feature.main.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bekmnsrw.fakestore.ui.theme.CustomTheme
import kotlin.math.ceil
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    DetailsContent(
        screenState = screenState.value,
        scrollBehavior = scrollBehavior,
        eventHandler = viewModel::eventHandler
    )

    DetailsActions(
        screenAction = screenAction,
        navController = navController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsContent(
    screenState: DetailsViewModel.DetailsScreenState,
    scrollBehavior: TopAppBarScrollBehavior,
    eventHandler: (DetailsViewModel.DetailsScreenEvent) -> Unit
) {

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DetailsScreenTopBar(
                scrollBehavior = scrollBehavior,
                eventHandler = eventHandler,
                isFavorite = screenState.isFavorite,
                productId = screenState.productDetails?.id ?: 0L
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            screenState.productDetails?.let {
                ImagesCarousel(
                    images = it.images
                )

                ProductTitleDetails(
                    title = it.title
                )

                ProductRatingAndNumberOfOrders(
                    rating = it.rating,
                    numberOfComments = it.stock,
                    numberOfOrders = it.stock
                )

                ProductPriceDetails(
                    fullPrice = it.price,
                    discountPrice = it.discountPercentage
                )

                IconWithText(
                    icon = Icons.Rounded.Done,
                    backgroundColor = CustomTheme.colors.rate,
                    text = "Осталось ${it.stock} штук"
                )

                IconWithText(
                    icon = Icons.Rounded.AddShoppingCart,
                    backgroundColor = CustomTheme.colors.rate,
                    text = "${it.stock} человек купили на этой неделе"
                )

                ProductDescription(
                    description = it.description
                )
            }
        }
    }

    CircularProgressBar(
        shouldShow = screenState.isLoading
    )
}

@Composable
fun ImagesCarousel(
    images: List<String>
) {

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = images[0])
                .crossfade(enable = true)
                .diskCachePolicy(policy = CachePolicy.ENABLED)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ProductTitleDetails(
    title: String
) {

    Text(
        text = title
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductRatingAndNumberOfOrders(
    rating: Double,
    numberOfComments: Long,
    numberOfOrders: Long
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CustomTheme.colors.background
            ),
            border = BorderStroke(
                width = 1.dp,
                color = CustomTheme.colors.bottomAppBarItemUnselected
            ),
            onClick = {}
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "$rating"
                    )

                    RatingBar(
                        rating = rating
                    )
                }

                Text(
                    text = "$numberOfComments отзывов"
                )
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CustomTheme.colors.background
            ),
            border = BorderStroke(
                width = 1.dp,
                color = CustomTheme.colors.bottomAppBarItemUnselected
            ),
            onClick = {}
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                Text(
                    text = "$numberOfOrders+"
                )

                Text(
                    text = "заказов"
                )
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Double
) {

    val filledStars = floor(rating).toInt()
    val unfilledStars = (5 - ceil(rating)).toInt()
    val halfStars = !(rating.rem(1).equals(0.0))

    Row {

        repeat(filledStars) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = CustomTheme.colors.rate
            )
        }

        if (halfStars) {
            Icon(
                imageVector = Icons.Outlined.StarHalf,
                contentDescription = null,
                tint = CustomTheme.colors.rate
            )
        }

        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = null,
                tint = CustomTheme.colors.rate
            )
        }
    }
}

@Composable
fun ProductPriceDetails(
    fullPrice: Double,
    discountPrice: Double
) {

    Row {

        Text(
            text = "$discountPrice $"
        )

        Text(
            text = "$fullPrice $"
        )
    }
}

@Composable
fun IconWithText(
    icon: ImageVector,
    backgroundColor: Color,
    text: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = backgroundColor)
                .padding(4.dp)
        )

        Text(
            text = text
        )
    }
}

@Composable
fun ProductDescription(
    description: String
) {

    Text(
        text = description
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    eventHandler: (DetailsViewModel.DetailsScreenEvent) -> Unit,
    isFavorite: Boolean,
    productId: Long
) = MediumTopAppBar(
    title = {

    },
    actions = {
        IconButton(
            onClick = { eventHandler(DetailsViewModel.DetailsScreenEvent.OnFavoriteClicked(productId)) }
        ) {

            Icon(
                imageVector = when (isFavorite) {
                    true -> Icons.Rounded.Favorite
                    false -> Icons.Rounded.FavoriteBorder
                },
                contentDescription = null,
                tint = when (isFavorite) {
                    true -> CustomTheme.colors.isFavoriteSelected
                    false -> CustomTheme.colors.isFavoriteUnselected
                }
            )
        }
    },
    navigationIcon = {
        IconButton(
            onClick = { eventHandler(DetailsViewModel.DetailsScreenEvent.OnArrowBackClicked) }
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
fun DetailsActions(
    screenAction: DetailsViewModel.DetailsScreenAction?,
    navController: NavController
) {

    LaunchedEffect(screenAction) {
        when (screenAction) {
            null -> Unit
            DetailsViewModel.DetailsScreenAction.NavigateBack -> navController.navigateUp()
        }
    }
}
