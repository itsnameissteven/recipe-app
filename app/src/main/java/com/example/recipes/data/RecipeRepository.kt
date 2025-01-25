package com.example.recipes.data

import com.example.recipes.model.Recipe
import com.example.recipes.model.RecipesResponse
import com.example.recipes.model.SearchResponse
import com.example.recipes.network.RecipeApiService

interface RecipeRepository {
    suspend fun getRecipes(): RecipesResponse
    suspend fun getRecipe(id: Int): Recipe
    suspend fun searchRecipes(query: String): SearchResponse
    suspend fun getFavorites(ids: String): List<Recipe>
}

class NetworkRecipeRepository(
    private val recipeApiService: RecipeApiService
) : RecipeRepository {

    override suspend fun getRecipes(): RecipesResponse = recipeApiService.getRecipes()
    override suspend fun getRecipe(id: Int): Recipe = recipeApiService.getRecipe(id)
    override suspend fun searchRecipes(query: String): SearchResponse =  recipeApiService.searchRecipes(query)
    override suspend fun getFavorites(ids: String): List<Recipe> = recipeApiService.getFavorites(ids)
}
