package com.example.photos.network

import com.example.photos.model.Photo
import retrofit2.http.GET

interface PhotoApiService {

    @GET("photos")
    suspend fun getPhotos(): List<Photo>
}
