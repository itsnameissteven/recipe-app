package com.mancr.recipes.network

import com.mancr.recipes.model.Recipe
import com.mancr.recipes.model.RecipesResponse
import com.mancr.recipes.model.SearchResponse
import com.mancr.recipes.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val apiKey = BuildConfig.API_KEY
// Fetch functions supported by the Retrofit library
interface RecipeApiService {
    // return arbitrary number of recipes to keep api cost free
    @GET("random?number=10&apiKey=$apiKey")
    suspend fun getRecipes(): RecipesResponse

    @GET("{id}/information?apiKey=$apiKey")
    suspend fun getRecipe(@Path("id") id: Int): Recipe

    @GET("complexSearch")
    suspend fun searchRecipes(@Query("query") query: String,
                              @Query("number") number: Int = 10,
                              @Query("apiKey") apiKey: String = BuildConfig.API_KEY
                    ): SearchResponse
    @GET("informationBulk")
    suspend fun getFavorites(@Query("ids") ids: String,
                             @Query("apiKey") apiKey: String = BuildConfig.API_KEY): List<Recipe>
}
