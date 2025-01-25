package com.example.recipes.data

import com.example.recipes.network.RecipeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val recipeRepository: RecipeRepository
}
// Create retrofit service for consumption by the network repository
class DefaultAppContainer: AppContainer {
    private val baseUrl = "https://api.spoonacular.com/recipes/"

    // Prevent errors when not including all keys in data classes
    private val retroJson = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(retroJson.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: RecipeApiService by lazy {
        retrofit.create(RecipeApiService::class.java)
    }

    override val recipeRepository: RecipeRepository by lazy {
        NetworkRecipeRepository(retrofitService)
    }
}
