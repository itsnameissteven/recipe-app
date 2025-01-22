package com.example.recipes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe (
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    @SerialName("extendedIngredients")
    val ingredients: List<Ingredient>,
    val instructions: String
)

@Serializable
data class Ingredient (
    val id: Int,
    val name: String,
    @SerialName("original")
    val amount: String
)

@Serializable
data class RecipesResponse (
    val recipes: List<Recipe>
)
