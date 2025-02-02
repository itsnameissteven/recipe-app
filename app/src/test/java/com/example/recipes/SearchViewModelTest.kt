package com.example.recipes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.recipes.data.RecipeRepository
import com.example.recipes.ui.screens.SearchUiState
import com.example.recipes.ui.screens.SearchViewModel
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
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var viewModel: SearchViewModel
    private val testRecipe = mockShallowRecipe
    private val detailedRecipes = mockSearchResponse
    private val mockQuery = mockFavoritesList.joinToString(",") { it.recipeId.toString()}

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        recipeRepository = mockk()
    }

    @Test
    fun `It should have an initial ui state`() = runTest {
        coEvery { recipeRepository.searchRecipes("test") } returns detailedRecipes

        viewModel = SearchViewModel(recipeRepository)
        advanceUntilIdle()

        assertEquals(SearchUiState.Initial, viewModel.state)
    }

    @Test
    fun `getFavorites success updates ui state`() = runTest {
        coEvery { recipeRepository.searchRecipes("test") } returns detailedRecipes

        viewModel = SearchViewModel(recipeRepository)
        viewModel.search("test")
        advanceUntilIdle()

        assertEquals(SearchUiState.Success(detailedRecipes), viewModel.state)
    }

    @Test
    fun `getFavorites network error updates ui state to Error`() = runTest {
        coEvery { recipeRepository.searchRecipes("test") } throws IOException()

        viewModel = SearchViewModel(recipeRepository)
        viewModel.search("test")
        advanceUntilIdle()

        assertEquals(SearchUiState.Error, viewModel.state)
    }

    @Test
    fun `getFavorites http error updates ui state to Error`() = runTest {


        coEvery { recipeRepository.searchRecipes("test") } throws HttpException(mockHTTPErrorResponse)

        viewModel = SearchViewModel(recipeRepository)
        viewModel.search("test")
        advanceUntilIdle()

        assertEquals(SearchUiState.Error, viewModel.state)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
