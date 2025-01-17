package com.example.photos

import android.app.Application
import com.example.photos.data.AppContainer
import com.example.photos.data.DefaultAppContainer

class PhotoApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
