package com.example.recipes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mancr.recipes.data.RecipeRepository
import com.mancr.recipes.ui.screens.RecipeUiState
import com.mancr.recipes.ui.screens.RecipeViewModel
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
class RecipeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var viewModel: RecipeViewModel
    private val testRecipe = mockShallowRecipe
    private val detailedRecipes = mockRecipeList

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        recipeRepository = mockk()
    }

    @Test
    fun `getRecipes success updates ui state`() = runTest {
        coEvery { recipeRepository.getRecipes() } returns detailedRecipes

        viewModel = RecipeViewModel(recipeRepository)
        advanceUntilIdle()

        assertEquals(RecipeUiState.Success(detailedRecipes), viewModel.recipesUiState)
    }

    @Test
    fun `getRecipes network error updates ui state to Error`() = runTest {
        coEvery { recipeRepository.getRecipes() } throws IOException()

        viewModel = RecipeViewModel(recipeRepository)
        advanceUntilIdle()

        assertEquals(RecipeUiState.Error, viewModel.recipesUiState)
    }

    @Test
    fun `getRecipes http error updates ui state to Error`() = runTest {


        coEvery { recipeRepository.getRecipes() } throws HttpException(mockHTTPErrorResponse)

        viewModel = RecipeViewModel(recipeRepository)
        advanceUntilIdle()

        assertEquals(RecipeUiState.Error, viewModel.recipesUiState)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

