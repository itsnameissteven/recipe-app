package com.example.recipes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.recipes.data.RecipeRepository
import com.example.recipes.ui.screens.RecipeDetailUiState
import com.example.recipes.ui.screens.RecipeDetailViewModel
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
class RecipeDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var viewModel: RecipeDetailViewModel
    private val testRecipe = mockShallowRecipe
    private val detailedRecipe = mockRecipe

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        recipeRepository = mockk()
    }

    @Test
    fun `getRecipe success updates ui state`() = runTest {
        coEvery { recipeRepository.getRecipe(716429) } returns detailedRecipe

        viewModel = RecipeDetailViewModel(recipeRepository, testRecipe)
        advanceUntilIdle()

        assertEquals(RecipeDetailUiState.Success(detailedRecipe), viewModel.recipeDetailsUiState)
    }

    @Test
    fun `getRecipe network error updates ui state to Error`() = runTest {
        coEvery { recipeRepository.getRecipe(716429) } throws IOException()

        viewModel = RecipeDetailViewModel(recipeRepository, testRecipe)
        advanceUntilIdle()

        assertEquals(RecipeDetailUiState.Error, viewModel.recipeDetailsUiState)
    }

    @Test
    fun `getRecipe http error updates ui state to Error`() = runTest {


        coEvery { recipeRepository.getRecipe(7) } throws HttpException(mockHTTPErrorResponse)

        viewModel = RecipeDetailViewModel(recipeRepository, testRecipe)
        advanceUntilIdle()

        assertEquals(RecipeDetailUiState.Error, viewModel.recipeDetailsUiState)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
