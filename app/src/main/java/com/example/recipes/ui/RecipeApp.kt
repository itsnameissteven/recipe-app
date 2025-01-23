package com.example.recipes.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.material3.Text
import com.example.recipes.R
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.recipes.ui.screens.HomeScreen
import com.example.recipes.ui.screens.RecipeDetailScreen
import com.example.recipes.ui.screens.RecipeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.recipes.model.Recipe
import com.example.recipes.ui.screens.BottomNavigationBar
import com.example.recipes.ui.screens.RecipeDetailViewModel
import com.example.recipes.ui.screens.SearchScreen
import com.example.recipes.ui.screens.SearchViewModel


@Composable
fun RecipeApp(onCardClick: (Recipe) -> Unit, selectedItem: MutableState<Int>) {
    AppScaffold(
       text = stringResource(R.string.app_name), selectedItem = selectedItem
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().padding(),
        ) {
            val recipesViewModel: RecipeViewModel  =
                viewModel(factory = RecipeViewModel.Factory)
            HomeScreen(
                recipeUiState = recipesViewModel.recipesUiState,
                retryAction = recipesViewModel::getAppRecipes,
                contentPadding = it,
                onCardClick = onCardClick
            )
        }
    }
}


@Composable
fun RecipeDetail(recipe: Recipe, onBackClick: () -> Unit, selectedItem: MutableState<Int>) {
    AppScaffold(selectedItem = selectedItem) { contentPadding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
        ) {
            val recipeDetailsViewModel: RecipeDetailViewModel  =
                viewModel(factory = RecipeDetailViewModel.provideFactory(recipe))
            fun getRecipe(recipe: Recipe) {
                recipeDetailsViewModel.getRecipe(recipe)
            }
            RecipeDetailScreen(
                recipeDetailUiState = recipeDetailsViewModel.recipeDetailsUiState,
                retryAction = { getRecipe(recipe) },
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
fun SearchDetails(onCardClick: (Recipe) -> Unit, selectedItem: MutableState<Int>) {
    AppScaffold(selectedItem = selectedItem) {
        Surface(
            modifier = Modifier.fillMaxSize().padding(it),
        ) {
            val searchViewModel: SearchViewModel =
                viewModel(factory = SearchViewModel.Factory)
            SearchScreen(
                state = searchViewModel.state,
                retryAction = {},
                contentPadding = it,
                onCardClick = onCardClick,
                onSearch = searchViewModel::search
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(text: String = "",selectedItem: MutableState<Int>, content: @Composable (PaddingValues) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { RecipeAppTopBar(scrollBehavior = scrollBehavior, text = text) },
        bottomBar = { BottomNavigationBar(selectedItem = selectedItem) },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeAppTopBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier, text: String = "") {
    var expandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight
    if(text == "") {
        expandedHeight = 0.dp
    }
        CenterAlignedTopAppBar(
            expandedHeight = expandedHeight,
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            modifier = modifier
        )
}
