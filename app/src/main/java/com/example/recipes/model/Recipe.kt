package com.example.recipes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
interface ShallowRecipe {
    val id: Int
    val title: String
    val image: String
}
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
