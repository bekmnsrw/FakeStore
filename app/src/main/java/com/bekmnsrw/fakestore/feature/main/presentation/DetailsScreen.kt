package com.bekmnsrw.fakestore.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)

    DetailsContent(
        screenState = screenState.value
    )

    DetailsActions()
}

@Composable
fun DetailsContent(
    screenState: DetailsViewModel.DetailsScreenState
) {

    Column(
        modifier = Modifier.fillMaxSize()
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

            ProductPrice(

            )
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
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun ProductTitleDetails(
    title: String
) {

    Text(
        text = title
    )
}

@Composable
fun ProductRatingAndNumberOfOrders(
    rating: Double,
    numberOfComments: Long,
    numberOfOrders: Long
) {

}

@Composable
fun ProductPrice(

) {

}

@Composable
fun DetailsActions(

) {

}
