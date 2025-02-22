package com.mancr.recipes.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mancr.recipes.ui.screens.HomeScreen
import com.mancr.recipes.ui.screens.RecipeDetailScreen
import com.mancr.recipes.ui.screens.RecipeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mancr.recipes.model.Favorite
import com.mancr.recipes.model.Recipe
import com.mancr.recipes.model.ShallowRecipe
import com.mancr.recipes.ui.screens.FavoritesScreen
import com.mancr.recipes.ui.screens.FavoritesViewModel
import com.mancr.recipes.ui.screens.RecipeDetailViewModel
import com.mancr.recipes.ui.screens.SearchScreen
import com.mancr.recipes.ui.screens.SearchViewModel

// Each function create a viewModel and pairs it with its screen
@Composable
fun RecipeApp(onCardClick: (ShallowRecipe) -> Unit, contentPadding: PaddingValues) {
    val recipesViewModel: RecipeViewModel =
        viewModel(factory = RecipeViewModel.Factory)
    HomeScreen(
        recipeUiState = recipesViewModel.recipesUiState,
        retryAction = recipesViewModel::getAppRecipes,
        contentPadding = contentPadding,
        onCardClick = onCardClick
    )
}


@Composable
fun RecipeDetail(recipe: ShallowRecipe, onClick: (Recipe) -> Unit, isFavorite: Boolean) {
    val recipeDetailsViewModel: RecipeDetailViewModel =
        viewModel(factory = RecipeDetailViewModel.provideFactory(recipe))
    fun getRecipe(recipe: ShallowRecipe) {
        recipeDetailsViewModel.getRecipe(recipe)
    }
    LaunchedEffect(Unit) {
        recipeDetailsViewModel.getRecipe(recipe)
    }
    RecipeDetailScreen(
        recipeDetailUiState = recipeDetailsViewModel.recipeDetailsUiState,
        retryAction = { getRecipe(recipe) },
        onClick = {onClick(it)},
        isFavorite = isFavorite
    )
}

@Composable
fun SearchDetails(onCardClick: (ShallowRecipe) -> Unit, contentPadding: PaddingValues) {
    val searchViewModel: SearchViewModel =
        viewModel(factory = SearchViewModel.Factory)
    SearchScreen(
        state = searchViewModel.state,
        retryAction = {},
        contentPadding = contentPadding,
        onCardClick = onCardClick,
        onSearch = searchViewModel::search
    )
}

@Composable
fun FavoritesDetail(favorites: List<Favorite>, contentPadding: PaddingValues, onCardClick: (ShallowRecipe) -> Unit) {
    val favoritesViewModel: FavoritesViewModel =
        viewModel(factory = FavoritesViewModel.providerFactory(favorites))
    // Make sure the screen is refreshed when the favorites change
    LaunchedEffect(Unit) {
        favoritesViewModel.getAppFavorites(favorites)
    }
    FavoritesScreen(
        state = favoritesViewModel.state,
        retryAction = {favoritesViewModel.getAppFavorites(favorites)},
        contentPadding = contentPadding,
        onCardClick = onCardClick
    )
}
