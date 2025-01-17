package com.example.photos.data

import com.example.photos.network.PhotoApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val photoRepository: PhotoRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: PhotoApiService by lazy {
        retrofit.create(PhotoApiService::class.java)
    }

    override val photoRepository: PhotoRepository by lazy {
        NetworkPhotoRepository(retrofitService)
    }
}
