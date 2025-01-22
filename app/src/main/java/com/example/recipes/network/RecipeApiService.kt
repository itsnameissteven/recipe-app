package com.example.recipes.network

import com.example.recipes.model.Recipe
import retrofit2.http.GET

interface RecipeApiService {

    @GET("list?page=3&limit=50")
    suspend fun getRecipes(): List<Recipe>
}
