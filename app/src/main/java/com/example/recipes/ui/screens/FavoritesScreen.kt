package com.example.recipes.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.recipes.model.ShallowRecipe


@Composable
fun FavoritesScreen(
    state: FavoritesUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCardClick: (ShallowRecipe) -> Unit,
) {
    when(state) {
        is FavoritesUiState.Loading -> LoadingScreen(modifier = modifier)
        is FavoritesUiState.Success -> {
            if(state.recipes.isEmpty()) {

                Text(
                    "No Recipes Saved",
                    modifier = Modifier.padding(top = 140.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            } else{
                RecipesGridScreen(
                    state.recipes,
                    contentPadding = contentPadding,
                    modifier = modifier.fillMaxWidth(),
                    onCardClick = onCardClick
                )
            }
        }
        is FavoritesUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxWidth())
    }
}
