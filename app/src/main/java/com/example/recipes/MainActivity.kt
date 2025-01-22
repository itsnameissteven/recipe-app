package com.example.recipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.recipes.model.Recipe
import com.example.recipes.ui.RecipeApp
import com.example.recipes.ui.RecipeDetail
import com.example.recipes.ui.theme.RecipeAppTheme


class MainActivity : ComponentActivity() {
    private var selectedRecipe: MutableState<Recipe?> = mutableStateOf(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            RecipeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if(selectedRecipe.value == null) {
                        RecipeApp(onCardClick = { recipe ->
                            selectedRecipe.value = recipe
                        })
                    } else {
                        RecipeDetail(selectedRecipe.value!!)
                    }
                }
            }
        }
    }
}
