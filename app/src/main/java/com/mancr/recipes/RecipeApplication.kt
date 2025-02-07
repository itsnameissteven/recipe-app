package com.mancr.recipes

import android.app.Application
import com.mancr.recipes.data.AppContainer
import com.mancr.recipes.data.DefaultAppContainer

class RecipeApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
