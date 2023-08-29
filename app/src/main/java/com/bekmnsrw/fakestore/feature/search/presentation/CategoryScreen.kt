package com.bekmnsrw.fakestore.feature.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bekmnsrw.fakestore.core.database.URLS
import com.bekmnsrw.fakestore.feature.main.presentation.list.CircularProgressBar
import com.bekmnsrw.fakestore.ui.theme.CustomTheme

@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel
) {

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)

    CategoryContent(
        screenState = screenState.value,
        eventHandler = viewModel::eventHandler
    )

    CategoryActions()
}

@Composable
fun CategoryContent(
    screenState: CategoryViewModel.CategoryScreenState,
    eventHandler: (CategoryViewModel.CategoryScreenEvent) -> Unit
) {

    CategoryList(
        categories = screenState.categories
    )

    CircularProgressBar(
        shouldShow = screenState.isLoading
    )
}

@Composable
fun CategoryList(
    categories: List<String>
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.background)
    ) {

        items(
            items = categories,
            key = { it }
        ) {

            CategoryListItem(
                category = it
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListItem(
    category: String
) {

    Card(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(size = 16.dp),
        onClick = {}
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = URLS[category]?.second)
                    .crossfade(enable = true)
                    .diskCachePolicy(policy = CachePolicy.ENABLED)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = URLS[category]?.first.toString(),
                style = CustomTheme.typography.categoryCard,
                color = CustomTheme.colors.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
            )
        }
    }
}

@Composable
fun CategoryActions() {}
