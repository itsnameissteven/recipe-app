package com.example.recipes

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.recipes.data.DataManager
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.robolectric.RobolectricTestRunner
import org.junit.runner.RunWith

@RunWith(RobolectricTestRunner::class)
class DataManagerTest {

    private lateinit var dataManager: DataManager
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        dataManager = DataManager(context)
    }

    @After
    fun tearDown() {
        context.deleteDatabase("favorites")
    }

    @Test
    fun `It should insert a favorite recipe`() {
        dataManager.insert(1024)

        val favorites = dataManager.selectAll()
        assertEquals(1, favorites.size)
        assertEquals(1024, favorites[0].recipeId)
    }

    @Test
    fun `It should delete a favorite recipe`() {
        dataManager.insert(1024)
        assertEquals(1, dataManager.selectAll().size)

        dataManager.delete(1024)

        assertEquals(0, dataManager.selectAll().size)
    }

    @Test
    fun `It should insert multiple recipes`() {
        dataManager.insert(111)
        dataManager.insert(222)

        val favorites = dataManager.selectAll()

        assertEquals(2, favorites.size)
        assertEquals(111, favorites[0].recipeId)
        assertEquals(222, favorites[1].recipeId)
    }
}
