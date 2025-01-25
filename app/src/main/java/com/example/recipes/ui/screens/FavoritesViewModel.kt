package com.example.recipes.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.recipes.RecipeApplication
import com.example.recipes.data.RecipeRepository
import com.example.recipes.model.Favorite
import com.example.recipes.model.Recipe
import com.example.recipes.model.RecipesResponse
import com.example.recipes.model.ShallowRecipe
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
        if (favorites.isEmpty()) {
            state = FavoritesUiState.Success(emptyList())
            return
        }
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
