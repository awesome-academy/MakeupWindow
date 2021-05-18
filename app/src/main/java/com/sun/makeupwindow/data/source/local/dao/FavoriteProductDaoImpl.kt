package com.sun.makeupwindow.data.source.local.dao

import android.content.ContentValues
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.local.db.AppDatabase
import com.sun.makeupwindow.utlis.INNER_JOIN

class FavoriteProductDaoImpl private constructor(
    private val appDatabase: AppDatabase
) : FavoriteProductDao {

    private val writableDatabase = appDatabase.writableDatabase
    private val readableDatabase = appDatabase.readableDatabase

    override fun getProducts(): List<Product> {
        val listProduct = mutableListOf<Product>()
        val query =
            "SELECT * FROM ${Product.TABLE_FAVORITE_NAME} ${INNER_JOIN} ${Product.TABLE_NAME} " +
                    "ON ${Product.TABLE_FAVORITE_NAME}.${Product.ID_FAVORITE_PRODUCT} = " +
                    "${Product.TABLE_NAME}.${Product.ID};"
        val cursor = readableDatabase.rawQuery(query, null)
        cursor.use {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val listColor = ColorDaoImpl.getInstance(appDatabase)
                    .getColor(it.getInt(it.getColumnIndex(Product.TABLE_NAME + "." + Product.ID)))
                listProduct.add(Product(it, listColor))
                it.moveToNext()
            }
        }
        return listProduct
    }

    override fun insertProduct(id: Int): Boolean {
        val value = ContentValues().apply {
            put(Product.ID_FAVORITE_PRODUCT, id)
        }
        return writableDatabase.insert(
            Product.TABLE_FAVORITE_NAME,
            null,
            value
        ) > 0
    }

    override fun deleteProduct(id: Int): Boolean {
        val selection = Product.ID_FAVORITE_PRODUCT + " LIKE ?"
        val selectionArgs = arrayOf(id.toString())
        return writableDatabase.delete(
            Product.TABLE_FAVORITE_NAME,
            selection,
            selectionArgs
        ) > 0
    }

    companion object {
        private var instance: FavoriteProductDaoImpl? = null

        fun getInstance(database: AppDatabase): FavoriteProductDaoImpl =
            instance ?: FavoriteProductDaoImpl(database).also {
                instance = it
            }
    }
}

