package com.example.recipes

import com.example.recipes.model.Ingredient
import com.example.recipes.model.Recipe
import com.example.recipes.model.ShallowRecipe
import com.example.recipes.model.RecipesResponse
import io.mockk.every
import io.mockk.mockk
import retrofit2.Response
import okhttp3.ResponseBody

data class MockShallowRecipe(
    override val id: Int,
    override val title: String,
    override val image: String
) : ShallowRecipe

val mockRecipe: Recipe = Recipe(
    id = 716429,
    title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
    image = "https://img.spoonacular.com/recipes/716429-556x370.jpg",
    summary = "Test Summary",
    ingredients = listOf(Ingredient(id = 1, name = "Test Ingredient", amount = "100g"), Ingredient(id = 2, name = "Test Ingredient 2", amount = "200g")),
    instructions = "Test Instructions"
)

val mockShallowRecipe: ShallowRecipe = MockShallowRecipe(
    id = 716429,
    title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
    image = "https://img.spoonacular.com/recipes/716429-556x370.jpg"
)

val mockHTTPErrorResponse = Response.error<String>(500, ResponseBody.create(null, ""))

val mockRecipeList: RecipesResponse = RecipesResponse(
    recipes = listOf(mockRecipe)
)

