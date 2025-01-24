package com.example.recipes.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.example.recipes.ui.screens.HomeScreen
import com.example.recipes.ui.screens.RecipeDetailScreen
import com.example.recipes.ui.screens.RecipeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipes.model.ShallowRecipe
import com.example.recipes.ui.screens.RecipeDetailViewModel
import com.example.recipes.ui.screens.SearchScreen
import com.example.recipes.ui.screens.SearchViewModel


@Composable
fun RecipeApp(onCardClick: (ShallowRecipe) -> Unit, contentPadding: PaddingValues) {
    val recipesViewModel: RecipeViewModel  =
        viewModel(factory = RecipeViewModel.Factory)
    HomeScreen(
        recipeUiState = recipesViewModel.recipesUiState,
        retryAction = recipesViewModel::getAppRecipes,
        contentPadding = contentPadding,
        onCardClick = onCardClick
    )
}


@Composable
fun RecipeDetail(recipe: ShallowRecipe, onBackClick: () -> Unit) {
    val recipeDetailsViewModel: RecipeDetailViewModel  =
        viewModel(factory = RecipeDetailViewModel.provideFactory(recipe))
    fun getRecipe(recipe: ShallowRecipe) {
        recipeDetailsViewModel.getRecipe(recipe)
    }
    RecipeDetailScreen(
        recipeDetailUiState = recipeDetailsViewModel.recipeDetailsUiState,
        retryAction = { getRecipe(recipe) },
        onBackClick = onBackClick
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
