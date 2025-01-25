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
import com.example.recipes.model.Recipe
import com.example.recipes.model.ShallowRecipe
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface RecipeDetailUiState {
    data class Success(val recipe: Recipe) : RecipeDetailUiState
    object Error : RecipeDetailUiState
    object Loading : RecipeDetailUiState
}

class RecipeDetailViewModel(private val recipeRepository: RecipeRepository, recipe: ShallowRecipe) : ViewModel() {
    var recipeDetailsUiState: RecipeDetailUiState by mutableStateOf(RecipeDetailUiState.Loading)
        private set

    init {
        getRecipe(recipe)
    }

    fun getRecipe(recipe: ShallowRecipe) {
        viewModelScope.launch {
            recipeDetailsUiState = RecipeDetailUiState.Loading
                recipeDetailsUiState = try {
                    RecipeDetailUiState.Success(recipeRepository.getRecipe(recipe.id))
                } catch (e: IOException) {
                    RecipeDetailUiState.Error
                } catch (e: HttpException) {
                    RecipeDetailUiState.Error
                } catch (e: Exception) {
                    RecipeDetailUiState.Error
                }
        }
    }

    companion object {
        // Factory function so a recipe can be passed in
        fun provideFactory (recipe: ShallowRecipe): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RecipeApplication)
                val recipeRepository = application.container.recipeRepository
                RecipeDetailViewModel(recipeRepository = recipeRepository, recipe = recipe)
            }
        }
    }
}
