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
import com.example.recipes.model.SearchResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SearchUiState {
    data class Success(val recipes: SearchResponse) : SearchUiState
    object Initial : SearchUiState
    object Error : SearchUiState
    object Loading : SearchUiState
}

class SearchViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {
    var state: SearchUiState by mutableStateOf(SearchUiState.Initial)
        private set

    init {
        searchLaunch()
    }

    fun searchLaunch() {
        viewModelScope.launch {
            state = SearchUiState.Initial
//            state = try {
//                RecipeUiState.Success(recipeRepository.getRecipes())
//            } catch (e: IOException) {
//                RecipeUiState.Error
//            } catch (e: HttpException) {
//                RecipeUiState.Error
//            } catch (e: Exception) {
//                RecipeUiState.Error
//            }
        }
    }
    fun search(query: String) {
        Log.i("Inner text", "This is runnint")
        viewModelScope.launch {
            state = SearchUiState.Loading
            state = try {
                SearchUiState.Success(recipeRepository.searchRecipes(query))
            } catch (e: IOException) {
                Log.i("Search1", e.toString())
                SearchUiState.Error
            } catch (e: HttpException) {
                Log.i("Search2", e.toString())
                SearchUiState.Error
            } catch (e: Exception) {
                Log.i("Search3", e.toString())
                SearchUiState.Error

            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RecipeApplication)
                val recipeRepository = application.container.recipeRepository
                SearchViewModel(recipeRepository = recipeRepository)
            }
        }

    }
}
