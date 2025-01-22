package com.example.recipes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe (
    val id: Int,
    val title: String,
    val image: String
)

@Serializable
data class RecipesResponse (
    val recipes: List<Recipe>
)
