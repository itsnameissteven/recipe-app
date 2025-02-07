package com.mancr.recipes.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import com.mancr.recipes.model.ShallowRecipe


@Composable
fun HomeScreen(
    recipeUiState: RecipeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCardClick: (ShallowRecipe) -> Unit,
) {
    when(recipeUiState) {
        is RecipeUiState.Loading -> LoadingScreen(modifier = modifier)
        is RecipeUiState.Success -> RecipesGridScreen(recipeUiState.recipes.recipes, contentPadding = contentPadding, modifier = modifier.fillMaxWidth(), onCardClick = onCardClick)
        is RecipeUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxWidth())
    }
}


