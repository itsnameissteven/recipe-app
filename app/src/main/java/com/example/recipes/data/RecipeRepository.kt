package com.example.recipes.data

import com.example.recipes.model.Recipe
import com.example.recipes.model.RecipesResponse
import com.example.recipes.network.RecipeApiService

interface RecipeRepository {
    suspend fun getRecipes(): RecipesResponse
    suspend fun getRecipe(id: Int): Recipe
}

class NetworkRecipeRepository(
    private val recipeApiService: RecipeApiService
) : RecipeRepository {

    override suspend fun getRecipes(): RecipesResponse = recipeApiService.getRecipes()
    override suspend fun getRecipe(id: Int): Recipe = recipeApiService.getRecipe(id)
}
