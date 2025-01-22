package com.example.recipes.ui.screens

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.recipes.model.Recipe
import com.example.recipes.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import com.example.recipes.model.RecipesResponse
import com.example.recipes.ui.RecipeApp
import com.example.recipes.ui.theme.RecipeAppTheme
import androidx.activity.compose.setContent
import com.example.recipes.MainActivity

@Composable
fun HomeScreen(
    recipeUiState: RecipeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCardClick: (Recipe) -> Unit
) {
    Log.i("test", "home screen")
    when(recipeUiState) {
        is RecipeUiState.Loading -> LoadingScreen(modifier = modifier)
        is RecipeUiState.Success -> RecipesGridScreen(recipeUiState.recipes, contentPadding = contentPadding, modifier = modifier.fillMaxWidth(), onCardClick = onCardClick)
        is RecipeUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun RecipesGridScreen(
    data: RecipesResponse,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCardClick: (Recipe) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {
        items(items = data.recipes, key = { recipe -> recipe.id }) { recipe ->
            RecipeCard(
                recipe,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f),
                onCardClick = {   onCardClick(recipe) },
            )
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, modifier: Modifier = Modifier, onCardClick: (Recipe) -> Unit) {
    Card(
        onClick = { onCardClick(recipe) },
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(recipe.image)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
