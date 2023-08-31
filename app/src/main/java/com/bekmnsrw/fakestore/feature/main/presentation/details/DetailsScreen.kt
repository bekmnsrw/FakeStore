package com.bekmnsrw.fakestore.feature.main.presentation.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bekmnsrw.fakestore.R
import com.bekmnsrw.fakestore.feature.main.presentation.list.CircularProgressBar
import com.bekmnsrw.fakestore.ui.theme.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomTheme.colors.background)
                .padding(contentPadding)
        ) {

            item {
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
                        backgroundColor = CustomTheme.colors.inStockIcon,
                        text = "Осталось ${it.stock} штук"
                    )

                    IconWithText(
                        icon = Icons.Rounded.AddShoppingCart,
                        backgroundColor = CustomTheme.colors.boughtIcon,
                        text = "${it.stock} человек купили на этой неделе"
                    )

                    ProductDescription(
                        description = it.description
                    )

                    Seller(
                        name = it.brand,
                        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXgAI0I_2cih4qpbeYgSQWKd2bbjbZUKoG4g&usqp=CAU",
                        rating = it.rating,
                        count = it.stock
                    )

                    Category(
                        category = it.category
                    )
                }
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

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data = images[0])
            .crossfade(enable = true)
            .diskCachePolicy(policy = CachePolicy.ENABLED)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ProductTitleDetails(
    title: String
) {

    Text(
        text = title,
        style = CustomTheme.typography.detailsTitle,
        color = CustomTheme.colors.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
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
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
    ) {

        Card(
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(size = 16.dp),
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
                    .padding(all = 10.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 4.dp)
                ) {

                    Text(
                        text = "$rating",
                        style = CustomTheme.typography.detailsCardTitle,
                        color = CustomTheme.colors.onBackground
                    )

                    RatingBar(
                        rating = rating
                    )
                }

                Text(
                    text = "$numberOfComments отзывов",
                    color = CustomTheme.colors.cardSupportingText,
                    style = CustomTheme.typography.detailsCardSupportingText
                )
            }
        }

        Card(
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(size = 16.dp),
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
                    .padding(all = 10.dp)
            ) {

                Text(
                    text = "$numberOfOrders+",
                    color = CustomTheme.colors.onBackground,
                    style = CustomTheme.typography.detailsCardTitle
                )

                Text(
                    text = "заказов",
                    color = CustomTheme.colors.cardSupportingText,
                    style = CustomTheme.typography.detailsCardSupportingText
                )
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Double
) {

    val numberOfStars = calculateNumberOfStars(rating)

    Row {

        repeat(numberOfStars.first) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = CustomTheme.colors.rate
            )
        }

        if (numberOfStars.third) {
            Icon(
                imageVector = Icons.Outlined.StarHalf,
                contentDescription = null,
                tint = CustomTheme.colors.rate
            )
        }

        repeat(numberOfStars.second) {
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        Text(
            text = "$discountPrice $",
            color = CustomTheme.colors.onBackground,
            style = CustomTheme.typography.detailsDiscountPrice
        )

        Text(
            text = "$fullPrice $",
            color = CustomTheme.colors.cardSupportingText,
            style = CustomTheme.typography.detailsFullPrice
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(size = 40.dp)
                .clip(RoundedCornerShape(size = 8.dp))
                .background(color = backgroundColor)
                .padding(all = 8.dp)
        )

        Text(
            text = text,
            color = CustomTheme.colors.onBackground,
            style = CustomTheme.typography.detailsListItem
        )
    }
}

@Composable
fun ProductDescription(
    description: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        Text(
            text = stringResource(id = R.string.description_title),
            style = CustomTheme.typography.detailsTitle,
            color = CustomTheme.colors.onBackground
        )

        Text(
            text = description,
            color = CustomTheme.colors.onBackground,
            style = CustomTheme.typography.detailsDescription,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Seller(
    name: String,
    image: String,
    rating: Double,
    count: Long
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        Text(
            text = stringResource(id = R.string.seller_title),
            style = CustomTheme.typography.detailsTitle,
            color = CustomTheme.colors.onBackground
        )

        Card(
            shape = RoundedCornerShape(size = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CustomTheme.colors.background
            ),
            border = BorderStroke(
                width = 1.dp,
                color = CustomTheme.colors.bottomAppBarItemUnselected
            ),
            onClick = {}
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data = image)
                        .crossfade(enable = true)
                        .diskCachePolicy(policy = CachePolicy.ENABLED)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(size = 52.dp)
                        .clip(RoundedCornerShape(size = 8.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {

                    Text(
                        text = name,
                        style = CustomTheme.typography.detailsCardTitle,
                        color = CustomTheme.colors.onBackground
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = CustomTheme.colors.rate,
                            modifier = Modifier.offset(x = (-2).dp)
                        )

                        Text(
                            text = "$rating",
                            color = CustomTheme.colors.onBackground,
                            style = CustomTheme.typography.detailsCardTitle
                        )

                        Text(
                            text = "$count оценки",
                            color = CustomTheme.colors.cardSupportingText,
                            style = CustomTheme.typography.detailsCardSupportingText,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Category(
    category: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
        shape = RoundedCornerShape(size = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CustomTheme.colors.background
        ),
        border = BorderStroke(
            width = 1.dp,
            color = CustomTheme.colors.bottomAppBarItemUnselected
        ),
        onClick = {}
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
        ) {

            Column {

                Text(
                    text = category.replaceFirstChar { it.uppercaseChar() },
                    color = CustomTheme.colors.onBackground,
                    style = CustomTheme.typography.detailsCardTitle
                )

                Text(
                    text = stringResource(id = R.string.category_title),
                    color = CustomTheme.colors.cardSupportingText,
                    style = CustomTheme.typography.detailsCardSupportingText,
                )
            }

            Icon(
                imageVector = Icons.Outlined.ArrowForwardIos,
                contentDescription = null,
                tint = CustomTheme.colors.cardSupportingText,
                modifier = Modifier.padding(start = 70.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    eventHandler: (DetailsViewModel.DetailsScreenEvent) -> Unit,
    isFavorite: Boolean,
    productId: Long
) = SmallTopAppBar(
    title = {},
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

fun calculateNumberOfStars(rating: Double): Triple<Int, Int, Boolean> {
    val splitRating = rating.toString().split(".")
    var numberBeforeDot = splitRating[0].toInt()
    val numberAfterDot = ("0." + splitRating[1]).toDouble()

    if (numberAfterDot > 0.79) numberBeforeDot++

    val hasHalfStar = when (numberAfterDot) {
        in 0.40..0.79 -> true
        else -> false
    }

    return Triple(
        first = numberBeforeDot,
        second = if (hasHalfStar) 4 - numberBeforeDot else 5 - numberBeforeDot,
        third = hasHalfStar
    )
}
