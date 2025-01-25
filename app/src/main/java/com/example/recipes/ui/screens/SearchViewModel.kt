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

    private fun searchLaunch() {
        viewModelScope.launch {
            state = SearchUiState.Initial
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            state = SearchUiState.Loading
            state = try {
                SearchUiState.Success(recipeRepository.searchRecipes(query))
            } catch (e: IOException) {
                SearchUiState.Error
            } catch (e: HttpException) {
                SearchUiState.Error
            } catch (e: Exception) {
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
