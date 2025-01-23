package com.example.recipes.network

import com.example.recipes.BuildConfig
import com.example.recipes.model.Recipe
import com.example.recipes.model.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Path

const val apiKey = BuildConfig.API_KEY
interface RecipeApiService {
    @GET("random?number=2&apiKey=${apiKey}")
    suspend fun getRecipes(): RecipesResponse

    @GET("{id}/information?apiKey=${apiKey}")
    suspend fun getRecipe(@Path("id") id: Int): Recipe
}
