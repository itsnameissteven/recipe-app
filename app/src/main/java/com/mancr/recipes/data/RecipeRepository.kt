package com.mancr.recipes.data

import com.mancr.recipes.model.Recipe
import com.mancr.recipes.model.RecipesResponse
import com.mancr.recipes.model.SearchResponse
import com.mancr.recipes.network.RecipeApiService
// Interface to consume the recipe API and return functions (the same ones as noted in recipeApiService)
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
