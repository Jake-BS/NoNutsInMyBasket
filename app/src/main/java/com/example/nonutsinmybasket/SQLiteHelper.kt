package com.example.nonutsinmybasket

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import com.example.nonutsinmybasket.models.Ingredient

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATEBASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATEBASE_NAME = "avoidListStorage.db"
        private const val TABLE_AVOID_CUSTOM = "table_avoid_custom"
        private const val ID = "id"
        private const val TABLE_DIETS = "table_diets"
        private const val NAME = "name"
        private const val DIET_NAME = "diet_name"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableAvoid = ("CREATE TABLE IF NOT EXISTS " + TABLE_AVOID_CUSTOM + " ("
                + ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT)")
        val createTableDiets = ("CREATE TABlE IF NOT EXISTS " + TABLE_DIETS + " ("
                + ID + " INTEGER PRIMARY KEY, " + DIET_NAME + " TEXT)")
        db?.execSQL(createTableAvoid)
        db?.execSQL(createTableDiets)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_DIETS")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_AVOID_CUSTOM")
        onCreate(db)
    }

    fun insertIngredient(ingredient: Ingredient): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, ingredient.name)

        val success = db.insert(TABLE_AVOID_CUSTOM, null, contentValues)
        db.close()
        return success
    }

    fun insertDiet(diet_name: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DIET_NAME, diet_name)

        val success = db.insert(TABLE_DIETS, null, contentValues)
        db.close()
        return success
    }

    fun getAllIngredients(): MutableList<Ingredient> {
        val avoidList: MutableList<Ingredient> = mutableListOf()
        val selectQuery = "SELECT * FROM $TABLE_AVOID_CUSTOM"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch ( e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return mutableListOf()
        }
        var id: Int
        var name: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("ingredient"))

                val ingredient = Ingredient(name)
                avoidList.add(ingredient)
            } while (cursor.moveToNext())
        }
        return avoidList
    }


    fun getAllDiets(): MutableList<String> {
        val diets: MutableList<String> = mutableListOf()
        val selectQuery = "SELECT * FROM $TABLE_DIETS"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch ( e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return mutableListOf()
        }
        var id: Int
        var diet_name: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                diet_name = cursor.getString(cursor.getColumnIndex("diet_name"))
                diets.add(diet_name)
            } while (cursor.moveToNext())
        }
        return diets
    }

    fun deleteDiet(diet_name: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DIET_NAME, diet_name)

        val success = db.delete(TABLE_DIETS, "diet_name='$diet_name'", null)
        db.close()
        return success

    }

    fun deleteIngredient(name: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NAME, name)

        val success = db.delete(TABLE_AVOID_CUSTOM, "name='$name'", null)
        db.close()
        return success

    }

}