package com.sun.makeupwindow.data.source.local.dao

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.local.db.AppDatabase

class ProductDAOImpl private constructor(
    private val appDatabase: AppDatabase
) : ProductDAO {

    private val writableDatabase = appDatabase.writableDatabase
    private val readableDatabase = appDatabase.readableDatabase

    override fun getProduct(): List<Product> {
        val listProduct = mutableListOf<Product>()
        val cursor =
            readableDatabase.query(Product.TABLE_NAME, null, null, null, null, null, null)

        cursor.use {
            while (cursor.moveToNext()) {
                val listColor = ColorDAOImpl.getInstance(appDatabase)
                    .getColor(it.getInt(it.getColumnIndex(Product.ID)))
                listProduct.add(Product(it, listColor))
            }
        }
        return listProduct
    }

    override fun getItemProduct(idproduct: Int): Product {
        val cursor = readableDatabase.query(
            false, Product.TABLE_NAME, null, Product.ID + "= ?",
            arrayOf(idproduct.toString()), null, null, null, null
        )
        val listColor = ColorDAOImpl.getInstance(appDatabase).getColor(idproduct)
        return Product(cursor, listColor)
    }

    override fun getProductByCategory(category: String): List<Product> {
        val listProduct = mutableListOf<Product>()
        val query =
            "SELECT * FROM ${Product.TABLE_NAME} WHERE ${Product.CATEGORY} LIKE '%${category}%'"
        val cursor =
            readableDatabase.rawQuery(query, null)
        cursor.use {
            while (cursor.moveToNext()) {
                val listColor = ColorDAOImpl.getInstance(appDatabase)
                    .getColor(it.getInt(it.getColumnIndex(Product.ID)))
                listProduct.add(Product(it, listColor))
            }
        }
        return listProduct
    }

    override fun addProduct(product: Product) =
        writableDatabase.insert(
            Product.TABLE_NAME,
            null,
            product.getContentValues()
        ) > 0 && ColorDAOImpl.getInstance(appDatabase).addColor(product.color, product.id)

    companion object {
        private var instance: ProductDAOImpl? = null

        fun getInstance(database: AppDatabase): ProductDAOImpl =
            instance ?: synchronized(this) {
                instance ?: ProductDAOImpl(database).also {
                    instance = it
                }
            }
    }
}
