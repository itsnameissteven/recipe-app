package com.example.recipes

import android.os.Bundle
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
import com.example.recipes.model.ShallowRecipe
import com.example.recipes.ui.RecipeApp
import com.example.recipes.ui.RecipeDetail
import com.example.recipes.ui.SearchDetails
import com.example.recipes.ui.screens.BottomNavigationBar
import com.example.recipes.ui.theme.RecipeAppTheme


class MainActivity : ComponentActivity() {
    private var selectedRecipe: MutableState<ShallowRecipe?> = mutableStateOf(null)
    var selectedItem: MutableState<Int> = mutableIntStateOf(0)

    fun setSelectedItem(item: Int) {
        selectedItem.value = item
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            RecipeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if(selectedRecipe.value != null) {
                        AppScaffold(
                            selectedItem = selectedItem.value,
                            setSelectedItem = { item -> setSelectedItem(item) }
                        ) {
                            RecipeDetail(
                                selectedRecipe.value!!,
                                onBackClick = { selectedRecipe.value = null },
                            )
                        }
                    } else {
                        if(selectedItem.value == 0) {
                            AppScaffold(
                                text = stringResource(R.string.app_name),
                                selectedItem = selectedItem.value,
                                setSelectedItem = { item -> setSelectedItem(item) }
                            ) {
                                RecipeApp(
                                    onCardClick = { recipe ->
                                        selectedRecipe.value = recipe
                                    },
                                    contentPadding = it
                                )
                            }
                            } else if(selectedItem.value == 2) {
                                AppScaffold(
                                    selectedItem = selectedItem.value,
                                    setSelectedItem = { item -> setSelectedItem(item) }
                                ) {
                                    SearchDetails(
                                        onCardClick = { recipe ->
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
