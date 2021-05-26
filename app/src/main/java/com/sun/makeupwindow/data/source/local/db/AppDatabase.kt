package com.sun.makeupwindow.data.source.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sun.makeupwindow.data.model.Color
import com.sun.makeupwindow.data.model.Product
import java.io.FileOutputStream

class AppDatabase private constructor(
    context: Context,
    name: String,
    version: Int
) : SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.apply {
            execSQL(CREATE_TABLE_PRODUCT)
            execSQL(CREATE_TABLE_PRODUCT_COLOR)
            execSQL(CREATE_TABLE_FAVORITE_PRODUCT)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE_TASK)
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "makeup.db"
        private const val DATABASE_VERSION = 2
        private const val CREATE_TABLE_PRODUCT =
            "CREATE TABLE " + Product.TABLE_NAME + " ( " +
                    Product.ID + " INTEGER PRIMARY KEY, " +
                    Product.BRAND + " TEXT, " +
                    Product.NAME + " TEXT, " +
                    Product.PRICE + " TEXT, " +
                    Product.PRICESIGN + " TEXT, " +
                    Product.CURRENCY + " TEXT, " +
                    Product.IMAGELINK + " TEXT, " +
                    Product.PRODUCTLINK + " TEXT, " +
                    Product.DESCRIPTION + " TEXT, " +
                    Product.PRODUCT_TYPE + " TEXT);"

        private const val CREATE_TABLE_PRODUCT_COLOR =
            "CREATE TABLE " + Color.TABLE_NAME + " ( " +
                    Color.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Color.HEXVALUE + " TEXT, " +
                    Color.COLOURNAME + " TEXT, " +
                    Color.IDPRODUCT + " INT, " +
                    "FOREIGN KEY (" + Color.IDPRODUCT + ") REFERENCES " + Product.TABLE_NAME + " (" + Product.ID + ")); "

        private const val CREATE_TABLE_FAVORITE_PRODUCT =
            "CREATE TABLE " + Product.TABLE_FAVORITE_NAME + " ( " +
                    Product.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Product.ID_FAVORITE_PRODUCT + " INT, " +
                    "FOREIGN KEY (" + Product.ID_FAVORITE_PRODUCT + ") REFERENCES " + Product.TABLE_NAME + " (" + Product.ID + ")); "

        private const val DROP_TABLE_TASK =
            "DROP TABLE IF EXISTS " + Color.TABLE_NAME +
                    ";DROP TABLE IF EXISTS " + Product.TABLE_FAVORITE_NAME +
                    ";DROP TABLE IF EXISTS " + Product.TABLE_NAME

        fun copyDatabase(context: Context) {
            val dbFile = context.getDatabasePath(AppDatabase.DATABASE_NAME)
            if (!dbFile.exists()) {
                val assetdb = context.assets.open(AppDatabase.DATABASE_NAME)
                val currentDB = FileOutputStream(dbFile)

                val buffer = ByteArray(1024)
                while (assetdb.read(buffer) > 0) {
                    currentDB.write(buffer)
                }

                currentDB.flush()
                currentDB.close()
                assetdb.close()
            }
        }

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
