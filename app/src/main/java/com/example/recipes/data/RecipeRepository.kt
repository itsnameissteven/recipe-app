package com.example.recipes.data

import com.example.recipes.model.Recipe
import com.example.recipes.network.RecipeApiService

interface RecipeRepository {
    suspend fun getRecipes(): List<Recipe>
}

class NetworkRecipeRepository(
    private val recipeApiService: RecipeApiService
) : RecipeRepository {

    override suspend fun getRecipes(): List<Recipe> = recipeApiService.getRecipes()
}
