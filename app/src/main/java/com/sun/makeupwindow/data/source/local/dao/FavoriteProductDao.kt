package com.sun.makeupwindow.data.source.local.dao

import com.sun.makeupwindow.data.model.Product

interface FavoriteProductDao {
    fun getProducts(): List<Product>
    fun insertProduct(productId: Int): Boolean
    fun deleteProduct(productId: Int): Boolean
}
