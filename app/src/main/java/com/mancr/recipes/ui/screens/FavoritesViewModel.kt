package com.mancr.recipes.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mancr.recipes.RecipeApplication
import com.mancr.recipes.data.RecipeRepository
import com.mancr.recipes.model.Favorite
import com.mancr.recipes.model.Recipe
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface FavoritesUiState {
    data class Success(val recipes: List<Recipe>) : FavoritesUiState
    object Error : FavoritesUiState
    object Loading : FavoritesUiState
}

class FavoritesViewModel(private val recipeRepository: RecipeRepository, favorites: List<Favorite>) : ViewModel() {
    var state: FavoritesUiState by mutableStateOf(FavoritesUiState.Loading)
        private set

    init {
        getAppFavorites(favorites)
    }

    fun getAppFavorites(favorites: List<Favorite>) {
        // No need to set loading state if favorite list is empty
        if (favorites.isEmpty()) {
            state = FavoritesUiState.Success(emptyList())
            return
        }
        // Format local favorites list to a query string
        val query = favorites.joinToString(",") { it.recipeId.toString()}
        viewModelScope.launch {
            state = FavoritesUiState.Loading
            state = try {
                FavoritesUiState.Success(recipeRepository.getFavorites(query))
            } catch (e: IOException) {
                FavoritesUiState.Error
            } catch (e: HttpException) {
                FavoritesUiState.Error
            } catch (e: Exception) {
                FavoritesUiState.Error
            }
        }
    }

    companion object {
        fun providerFactory (favorites: List<Favorite>): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RecipeApplication)
                val recipeRepository = application.container.recipeRepository
                FavoritesViewModel(recipeRepository = recipeRepository, favorites = favorites)
            }
        }
    }
}
