package com.example.recipes.network

import com.example.recipes.BuildConfig
import com.example.recipes.model.Recipe
import com.example.recipes.model.RecipesResponse
import com.example.recipes.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val apiKey = BuildConfig.API_KEY
interface RecipeApiService {
    @GET("random?number=2&apiKey=${apiKey}")
    suspend fun getRecipes(): RecipesResponse

    @GET("{id}/information?apiKey=${apiKey}")
    suspend fun getRecipe(@Path("id") id: Int): Recipe

    @GET("complexSearch")
    suspend fun searchRecipes(@Query("query") query: String,
                              @Query("number") number: Int = 2,
                              @Query("apiKey") apiKey: String = BuildConfig.API_KEY
                    ): SearchResponse
    @GET("informationBulk")
    suspend fun getFavorites(@Query("ids") ids: String,
                             @Query("apiKey") apiKey: String = BuildConfig.API_KEY): List<Recipe>
}
