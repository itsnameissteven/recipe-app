package com.example.photos.network

import com.example.photos.model.Photo
import retrofit2.http.GET

interface PhotoApiService {

    @GET("list?page=3&limit=50")
    suspend fun getPhotos(): List<Photo>
}
