package com.example.recipes.data

import com.example.recipes.model.RecipesResponse
import com.example.recipes.network.RecipeApiService

interface RecipeRepository {
    suspend fun getRecipes(): RecipesResponse
}

class NetworkRecipeRepository(
    private val recipeApiService: RecipeApiService
) : RecipeRepository {

    override suspend fun getRecipes(): RecipesResponse = recipeApiService.getRecipes()
}
