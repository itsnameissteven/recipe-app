package com.example.recipes.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.recipes.R
import com.example.recipes.model.Recipe
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.example.recipes.model.RecipesResponse
import com.example.recipes.model.SearchResponse

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Log.i("test", "error screen")
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun RecipeImage(recipe: Recipe, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current).data(recipe.image)
            .crossfade(true).build(),
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.app_name),
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}


@Composable
fun BottomNavigationBar(selectedItem: MutableState<Int>) {
    val items = listOf("Browse", "Favorites", "Search")
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.Search)
    val unselectedIcons =
        listOf(Icons.Outlined.Home, Icons.Outlined.FavoriteBorder, Icons.Outlined.Search)
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem.value == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                selected = selectedItem.value == index,
                onClick = { selectedItem.value = index }
            )
        }
    }
}

@Composable
fun RecipesGridScreen(
    data: RecipesResponse,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCardClick: (Recipe) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp, vertical = 4.dp),
        contentPadding = contentPadding
    ) {
        items(items = data.recipes, key = { recipe -> recipe.id }) { recipe ->
            RecipeCard(
                recipe,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                onCardClick = {   onCardClick(recipe) },
            )
        }
    }
}

@Composable
fun SearchRecipesGridScreen(
    data: SearchResponse,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCardClick: (Recipe) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp, vertical = 4.dp),
        contentPadding = contentPadding
    ) {
        items(items = data.results, key = { recipe -> recipe.id }) { recipe ->
//            todo look into generics here
            RecipeCard(
                recipe,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                onCardClick = {   onCardClick(recipe) },
            )
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, modifier: Modifier = Modifier, onCardClick: (Recipe) -> Unit) {
    Card(
        onClick = { onCardClick(recipe) },
        modifier = modifier.height(225.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

    ) {
        RecipeImage(recipe, modifier = Modifier.fillMaxWidth().aspectRatio(1.4f))
        Text(recipe.title, modifier = Modifier.padding(8.dp), style = MaterialTheme.typography.titleMedium)
    }
}
