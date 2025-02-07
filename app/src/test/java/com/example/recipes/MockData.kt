package com.example.recipes

import com.mancr.recipes.model.BaseRecipe
import com.mancr.recipes.model.Favorite
import com.mancr.recipes.model.Ingredient
import com.mancr.recipes.model.Recipe
import com.mancr.recipes.model.RecipesResponse
import com.mancr.recipes.model.SearchResponse
import retrofit2.Response
import okhttp3.ResponseBody

//data class MockShallowRecipe(
//    override val id: Int,
//    override val title: String,
//    override val image: String
//) : ShallowRecipe

val mockRecipe: Recipe = Recipe(
    id = 716429,
    title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
    image = "https://img.spoonacular.com/recipes/716429-556x370.jpg",
    summary = "Test Summary",
    ingredients = listOf(Ingredient(id = 1, name = "Test Ingredient", amount = "100g"), Ingredient(id = 2, name = "Test Ingredient 2", amount = "200g")),
    instructions = "Test Instructions"
)

val mockShallowRecipe: BaseRecipe = BaseRecipe(
    id = 716429,
    title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
    image = "https://img.spoonacular.com/recipes/716429-556x370.jpg"
)

val mockFavoritesList: List<Favorite> = listOf(Favorite(id = 1, recipeId = 716429))

val mockHTTPErrorResponse = Response.error<String>(500, ResponseBody.create(null, ""))

val mockRecipeList: RecipesResponse = RecipesResponse(
    recipes = listOf(mockRecipe)
)

val mockSearchResponse: SearchResponse = SearchResponse(
    results = listOf(mockShallowRecipe)
)


