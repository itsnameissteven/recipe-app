package com.example.recipes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.recipes.data.RecipeRepository
import com.example.recipes.ui.screens.FavoritesUiState
import com.example.recipes.ui.screens.FavoritesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var viewModel: FavoritesViewModel
    private val testRecipe = mockShallowRecipe
    private val detailedRecipes = mockRecipeList
    private val mockQuery = mockFavoritesList.joinToString(",") { it.recipeId.toString()}

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        recipeRepository = mockk()
    }

    @Test
    fun `getFavorites success updates ui state`() = runTest {
        coEvery { recipeRepository.getFavorites(mockQuery) } returns detailedRecipes.recipes

        viewModel = FavoritesViewModel(recipeRepository, mockFavoritesList)
        advanceUntilIdle()

        assertEquals(FavoritesUiState.Success(detailedRecipes.recipes), viewModel.state)
    }

    @Test
    fun `getFavorites network error updates ui state to Error`() = runTest {
        coEvery { recipeRepository.getFavorites(mockQuery) } throws IOException()

        viewModel = FavoritesViewModel(recipeRepository, mockFavoritesList)
        advanceUntilIdle()

        assertEquals(FavoritesUiState.Error, viewModel.state)
    }

    @Test
    fun `getFavorites http error updates ui state to Error`() = runTest {


        coEvery { recipeRepository.getFavorites(mockQuery) } throws HttpException(mockHTTPErrorResponse)

        viewModel = FavoritesViewModel(recipeRepository, mockFavoritesList)
        advanceUntilIdle()

        assertEquals(FavoritesUiState.Error, viewModel.state)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
