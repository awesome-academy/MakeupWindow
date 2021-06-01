package com.sun.makeupwindow.data.source.local.dao

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.local.db.AppDatabase
import com.sun.makeupwindow.utlis.LIMIT
import com.sun.makeupwindow.utlis.ONE

class ProductDaoImpl private constructor(
    private val appDatabase: AppDatabase
) : ProductDao {

    private val writableDatabase = appDatabase.writableDatabase
    private val readableDatabase = appDatabase.readableDatabase

    override fun getProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val cursor =
            readableDatabase.query(
                Product.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                LIMIT.toString()
            )

        cursor.use {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val listColor = ColorDaoImpl.getInstance(appDatabase)
                    .getColor(it.getInt(it.getColumnIndex(Product.ID)))
                products.add(Product(it, listColor))
                it.moveToNext()
            }
        }
        return products
    }

    override fun getProductsLoadMore(totalItem: Int): List<Product> {
        val products = mutableListOf<Product>()
        val cursor =
            readableDatabase.query(
                Product.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                totalItem.toString() + "," + LIMIT.toString()
            )

        cursor.use {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val listColor = ColorDaoImpl.getInstance(appDatabase)
                    .getColor(it.getInt(it.getColumnIndex(Product.ID)))
                products.add(Product(it, listColor))
                it.moveToNext()
            }
        }
        return products
    }

    override fun getLastId(): Int {
        val cursor =
            readableDatabase.query(
                false,
                Product.TABLE_NAME,
                arrayOf(Product.ID),
                null,
                null,
                null,
                null,
                Product.ID + " DESC",
                ONE
            )
        cursor.moveToFirst()
        return if (cursor.count == 0) 0 else cursor.getInt(cursor.getColumnIndex(Product.ID))
    }

    override fun getItemProduct(id: Int): Product {
        val cursor = readableDatabase.query(
            false, Product.TABLE_NAME, null, Product.ID + "= ?",
            arrayOf(id.toString()), null, null, null, null
        )
        val listColor = ColorDaoImpl.getInstance(appDatabase).getColor(id)
        return Product(cursor, listColor)
    }

    override fun getProductByCategory(category: String): List<Product> {
        val products = mutableListOf<Product>()
        val query =
            "SELECT * FROM ${Product.TABLE_NAME} WHERE ${Product.PRODUCT_TYPE} LIKE '%${category}%'"
        val cursor = readableDatabase.rawQuery(query, null)

        cursor.use {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val listColor = ColorDaoImpl.getInstance(appDatabase)
                    .getColor(it.getInt(it.getColumnIndex(Product.ID)))
                products.add(Product(it, listColor))
                it.moveToNext()
            }
        }
        return products
    }

    override fun addProduct(product: Product) =
        writableDatabase.insert(
            Product.TABLE_NAME,
            null,
            product.getContentValues()
        ) > 0 && ColorDaoImpl.getInstance(appDatabase).addColor(product.color, product.id)

    override fun searchProductsByWord(wordSearch: String): List<Product> {
        val products = mutableListOf<Product>()
        val query =
            "SELECT * FROM ${Product.TABLE_NAME} WHERE ${Product.NAME} LIKE '%${wordSearch}%' OR " +
                    "${Product.DESCRIPTION} LIKE '%${wordSearch}%' LIMIT ${LIMIT}"
        val cursor =
            readableDatabase.rawQuery(query, null)
        cursor.use {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val listColor = ColorDaoImpl.getInstance(appDatabase)
                    .getColor(it.getInt(it.getColumnIndex(Product.ID)))
                products.add(Product(it, listColor))
                it.moveToNext()
            }
        }
        return products
    }

    override fun searchProducts(wordSearch: String, totalItem: Int?): List<Product> {
        val products = mutableListOf<Product>()
        val query = "SELECT * FROM ${Product.TABLE_NAME} " +
                    "WHERE ${Product.NAME} " +
                    "LIKE '%${wordSearch}%' " +
                    "OR ${Product.DESCRIPTION} " +
                    "LIKE '%${wordSearch}%' " +
                    "LIMIT ${totalItem} , ${LIMIT}"

        val cursor = readableDatabase.rawQuery(query, null)

        cursor.use {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val listColor = ColorDaoImpl.getInstance(appDatabase)
                    .getColor(it.getInt(it.getColumnIndex(Product.ID)))
                products.add(Product(it, listColor))
                it.moveToNext()
            }
        }
        return products
    }

    override fun numberOfProducts(wordSearch: String): Int {
        val query = "SELECT * FROM ${Product.TABLE_NAME} " +
                    "WHERE ${Product.NAME} " +
                    "LIKE '%${wordSearch}%' " +
                    "OR ${Product.DESCRIPTION} " +
                    "LIKE '%${wordSearch}%'"
        val cursor = readableDatabase.rawQuery(query, null)
        return cursor.count
    }

    companion object {
        private var instance: ProductDaoImpl? = null

        fun getInstance(database: AppDatabase): ProductDaoImpl =
            instance ?: ProductDaoImpl(database).also {
                instance = it
            }
    }
}
