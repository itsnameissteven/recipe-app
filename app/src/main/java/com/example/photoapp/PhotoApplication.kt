package com.example.photoapp

import android.app.Application
import com.example.photoapp.data.AppContainer
import com.example.photoapp.data.DefaultAppContainer

class PhotoApp : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
