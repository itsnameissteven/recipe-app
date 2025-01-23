package com.example.recipes.ui.screens

import android.util.Log
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
import com.example.recipes.model.RecipesResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface RecipeUiState {
    data class Success(val recipes: RecipesResponse) : RecipeUiState
    object Error : RecipeUiState
    object Loading : RecipeUiState
}

class RecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {
    var recipesUiState: RecipeUiState by mutableStateOf(RecipeUiState.Loading)
        private set

    init {
       getAppRecipes()
   }

    fun getAppRecipes() {
        viewModelScope.launch {
            recipesUiState = RecipeUiState.Loading
            recipesUiState = try {
                RecipeUiState.Success(recipeRepository.getRecipes())
            } catch (e: IOException) {
                RecipeUiState.Error
            } catch (e: HttpException) {
                RecipeUiState.Error
            } catch (e: Exception) {
                RecipeUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RecipeApplication)
                val recipeRepository = application.container.recipeRepository
                RecipeViewModel(recipeRepository = recipeRepository)
            }
        }
    }
}
