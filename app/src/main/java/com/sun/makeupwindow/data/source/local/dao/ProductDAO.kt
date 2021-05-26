package com.sun.makeupwindow.data.source.local.dao

import com.sun.makeupwindow.data.model.Product

interface ProductDao {
    fun getProducts(): List<Product>
    fun getProductsLoadMore(totalItem: Int): List<Product>
    fun getLastId(): Int
    fun getItemProduct(id: Int): Product
    fun getProductByCategory(category: String): List<Product>
    fun addProduct(product: Product): Boolean
    fun searchProductsByWord(wordSearch: String): List<Product>
}
