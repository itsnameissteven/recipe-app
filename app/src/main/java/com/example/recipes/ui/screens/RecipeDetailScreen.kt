package com.example.recipes.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeDetailScreen(
    recipeDetailUiState: RecipeDetailUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Log.i("test", "home screen")
    when(recipeDetailUiState) {
        is RecipeDetailUiState.Loading -> LoadingScreen(modifier = modifier)
//        is RecipeUiState.Success -> RecipesGridScreen(recipeUiState.recipes, contentPadding = contentPadding, modifier = modifier.fillMaxWidth())
        is RecipeDetailUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxWidth())
    }
}
