package com.sun.makeupwindow.data.source.local.dao

import com.sun.makeupwindow.data.model.Product

interface ProductDAO {
    fun getProduct(): List<Product>
    fun getItemProduct(idproduct: Int): Product
    fun getProductByCategory(category: String): List<Product>
    fun addProduct(product: Product): Boolean

}
