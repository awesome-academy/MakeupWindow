package com.sun.makeupwindow.data.source.local.dao

import com.sun.makeupwindow.data.model.Product

interface FavoriteProductDao {
    fun getProducts(): List<Product>
    fun checkProduct(id: Int): Boolean
    fun insertProduct(id: Int): Boolean
    fun deleteProduct(id: Int): Boolean
}
