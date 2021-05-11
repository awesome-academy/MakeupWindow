package com.sun.makeupwindow.data.source.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sun.makeupwindow.data.model.Color
import com.sun.makeupwindow.data.model.Product

class AppDatabase private constructor(
    context: Context,
    name: String,
    version: Int
) : SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.apply {
            execSQL(CREATE_TABLE_PRODUCT)
            execSQL(CREATE_TABLE_PRODUCT_COLOR)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE_TASK)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "makeup.db"
        private const val DATABASE_VERSION = 2
        private const val CREATE_TABLE_PRODUCT =
            "CREATE TABLE " + Product.TABLE_NAME + " ( " +
                    Product.ID + " INT PRIMARY KEY, " +
                    Product.BRAND + " TEXT, " +
                    Product.NAME + " TEXT, " +
                    Product.PRICE + " FLOAT, " +
                    Product.PRICESIGN + " TEXT, " +
                    Product.CURRENCY + " TEXT, " +
                    Product.IMAGELINK + " TEXT, " +
                    Product.PRODUCTLINK + " TEXT, " +
                    Product.DESCRIPTION + " TEXT, " +
                    Product.CATEGORY + " TEXT);"

        private const val CREATE_TABLE_PRODUCT_COLOR =
            "CREATE TABLE " + Color.TABLE_NAME + " ( " +
                    Color.ID + " INT NOT NULL AUTO_INCREMENT, " +
                    Color.HEXVALUE + " TEXT, " +
                    Color.COLOURNAME + " TEXT, " +
                    Color.IDPRODUCT + " INT " +
                    "FOREIGN KEY (" + Color.IDPRODUCT + ") REFERENCES " + Product.TABLE_NAME + " (" + Product.ID + "), " +
                    "PRIMARY KEY (" + Color.ID + "));"

        private const val DROP_TABLE_TASK =
            "DROP TABLE IF EXISTS " + Color.TABLE_NAME +
                    ";DROP TABLE IF EXISTS " + Product.TABLE_NAME

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: AppDatabase(
                    context,
                    DATABASE_NAME,
                    DATABASE_VERSION
                ).also { instance = it }
            }
    }
}
