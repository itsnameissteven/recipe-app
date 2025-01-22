package com.example.recipes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe (
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerialName(value = "download_url")
    val imgSrc: String
)
