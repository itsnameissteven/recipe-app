package com.example.recipes.data

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.recipes.model.Favorite

class DataManager(context: Context) {
    private val db: SQLiteDatabase

    init {
        val helper = CustomSQLiteOpenHelper(context)
        db = helper.writableDatabase
    }

    companion object {
        const val TABLE_ROW_ID = "_id"
        const val TABLE_ROW_RECIPE_ID = "recipe_id"

        private const val DB_NAME = "favorites"
        private const val DB_VERSION = 1
        private const val TABLE_N = "favorites"
    }

    fun insert(recipeId: Int) {
        val query = "INSERT INTO $TABLE_N ($TABLE_ROW_RECIPE_ID) VALUES ($recipeId);"
        Log.i("insert", query)

        db.execSQL(query)
    }

    fun delete(id: Int){
        val query = "DELETE FROM $TABLE_N WHERE $TABLE_ROW_RECIPE_ID = '$id';"
        Log.i("delete", query)
        db.execSQL(query)
    }

    fun selectAll(): List<Favorite> {
        val favorites = mutableListOf<Favorite>()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_N;", null)
        cursor.use {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_ROW_ID))
                val recipeId = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_ROW_RECIPE_ID))
                favorites.add(Favorite(id, recipeId))
            }
            cursor.close()
        }

        return favorites
    }


    private inner class CustomSQLiteOpenHelper(
        context: Context
    ): SQLiteOpenHelper
        (context, DB_NAME, null, DB_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            val newTableQueryString = ("create table " +
                    TABLE_N + " ("
                    + TABLE_ROW_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_ROW_RECIPE_ID
                    + " integer not null);")
            db.execSQL(newTableQueryString)
        }
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        }
    }
}
