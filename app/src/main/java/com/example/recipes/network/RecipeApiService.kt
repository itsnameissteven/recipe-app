package com.example.recipes.network

import com.example.recipes.BuildConfig
import com.example.recipes.model.RecipesResponse
import retrofit2.http.GET
const val apiKey = BuildConfig.API_KEY
interface RecipeApiService {
    @GET("random?number=1&apiKey=${apiKey}")
    suspend fun getRecipes(): RecipesResponse
}
