package com.example.photos.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Photo (
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)
