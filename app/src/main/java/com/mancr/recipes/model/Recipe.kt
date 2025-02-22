package com.mancr.recipes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Shallow data type to be extended upon and used as a type in composables.
interface ShallowRecipe {
    val id: Int
    val title: String
    val image: String
}
// API data types
@Serializable
data class Recipe (
    override val id: Int,
    override val title: String,
    override val image: String,
    val summary: String,
    @SerialName("extendedIngredients")
    val ingredients: List<Ingredient>,
    val instructions: String
): ShallowRecipe

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

@Serializable
data class BaseRecipe (
    override val id: Int,
    override val title: String,
    override val image: String,
): ShallowRecipe

@Serializable
data class SearchResponse (
    val results: List<BaseRecipe>
)

@Serializable
data class Favorite (
    val id: Int,
    val recipeId: Int,
)
