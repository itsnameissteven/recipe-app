package com.example.recipes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.recipes.data.DataManager
import com.example.recipes.model.Favorite
import com.example.recipes.model.ShallowRecipe
import com.example.recipes.ui.FavoritesDetail
import com.example.recipes.ui.RecipeApp
import com.example.recipes.ui.RecipeDetail
import com.example.recipes.ui.SearchDetails
import com.example.recipes.ui.screens.BottomNavigationBar
import com.example.recipes.ui.theme.RecipeAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val selectedRecipe: MutableState<ShallowRecipe?> = mutableStateOf(null)
        val selectedItem: MutableState<Int> = mutableIntStateOf(0)
        val favorites: MutableState<List<Favorite>> = mutableStateOf(listOf())
        val dm = DataManager(this)
        favorites.value = dm.selectAll()

        fun setSelectedItem(item: Int) {
            selectedRecipe.value = null
            selectedItem.value = item
        }

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            RecipeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    // Always display the recipe detail page if it is selected
                    if(selectedRecipe.value != null) {
                        AppScaffold(
                            selectedItem = selectedItem.value,
                            setSelectedItem = { item -> setSelectedItem(item) }
                        ) {
                            RecipeDetail(
                                selectedRecipe.value!!,
                                isFavorite = favorites.value.any { it.recipeId == selectedRecipe.value!!.id },
                                onClick = { recipe ->
                                    if(favorites.value.any { it.recipeId == recipe.id }) {
                                        dm.delete(recipe.id)
                                    } else {
                                        dm.insert(recipe.id)
                                    }
                                    favorites.value = dm.selectAll()
                                }
                            )
                        }
                    } else {
                        // When no select recipe follow the navigation bar values
                        if(selectedItem.value == 0) {
                            AppScaffold(
                                text = stringResource(R.string.app_name),
                                selectedItem = selectedItem.value,
                                setSelectedItem = { item -> setSelectedItem(item) }
                            ) {
                                RecipeApp(
                                    onCardClick = { recipe ->
                                        selectedItem.value = 4
                                        selectedRecipe.value = recipe
                                    },
                                    contentPadding = it
                                )
                            }
                        } else if (selectedItem.value == 1) {
                            AppScaffold(
                                stringResource(R.string.favorites),
                                selectedItem = selectedItem.value,
                                setSelectedItem = { item -> setSelectedItem(item) }
                            ) {
                                FavoritesDetail(
                                    favorites = favorites.value,
                                    contentPadding = it,
                                    onCardClick = { recipe ->
                                        selectedItem.value = 4
                                        selectedRecipe.value = recipe
                                    }
                                )
                            }
                            } else if(selectedItem.value == 2) {
                                AppScaffold(
                                    selectedItem = selectedItem.value,
                                    setSelectedItem = { item -> setSelectedItem(item) }
                                ) {
                                    SearchDetails(
                                        onCardClick = { recipe ->
                                            selectedItem.value = 4
                                            selectedRecipe.value = recipe
                                        },
                                        contentPadding = it
                                    )
                                }
                            }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    text: String = "",
    selectedItem: Int,
    setSelectedItem: (Int) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { RecipeAppTopBar(scrollBehavior = scrollBehavior, text = text) },
        bottomBar = { BottomNavigationBar(selectedItem = selectedItem, setSelectedItem = setSelectedItem) },
        content = { paddingValues ->
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                content(paddingValues)
            }
        }
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeAppTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    text: String = ""
) {
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
