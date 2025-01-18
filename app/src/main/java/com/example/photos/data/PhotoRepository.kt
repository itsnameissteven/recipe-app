package com.example.photos.data

import com.example.photos.model.Photo
import com.example.photos.network.PhotoApiService

interface PhotoRepository {
    suspend fun getPhotos(): List<Photo>
}

class NetworkPhotoRepository(
    private val photoApiService: PhotoApiService
) : PhotoRepository {

    override suspend fun getPhotos(): List<Photo> = photoApiService.getPhotos()
}
