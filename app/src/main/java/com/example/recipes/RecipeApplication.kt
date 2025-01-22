package com.example.recipes

import android.app.Application
import com.example.recipes.data.AppContainer
import com.example.recipes.data.DefaultAppContainer

class RecipeApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
