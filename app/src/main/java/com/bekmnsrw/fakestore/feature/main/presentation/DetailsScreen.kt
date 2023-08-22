package com.bekmnsrw.fakestore.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

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

        Text(
            text = "Details"
        )

        Text(
            text = "${screenState.productDetails?.title}"
        )
    }
}

@Composable
fun DetailsActions() {

}
