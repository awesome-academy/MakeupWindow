package com.sun.makeupwindow.data.source

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

interface ProductDataSource {
    interface Local{
        fun getProducts(callback: OnDataLoadCallback<List<Product>>)
        fun getProductsLoadMore(totalItem: Int, callback: OnDataLoadCallback<List<Product>>)
        fun getLastId(callback: OnDataLoadCallback<Int>)
        fun getProductId(id: Int, callback: OnDataLoadCallback<Product>)
        fun getProductsByCategory(category: String, callback: OnDataLoadCallback<List<Product>>)
        fun addProduct(product: Product, callback: OnDataLoadCallback<Boolean>)
        fun searchProducts(wordSearch: String, callback: OnDataLoadCallback<List<Product>>)
        fun searchProductLoadMore(search: Map<String,Int>, callback: OnDataLoadCallback<List<Product>>)
        fun numberOfProducts(wordSearch: String, callback: OnDataLoadCallback<Int>)
    }

    interface Remote{
        fun getFirstProduct(callback: OnDataLoadCallback<List<Product>>)
        fun getProduct(callback: OnDataLoadCallback<List<Product>>)
        fun getProductById(id: Int, callback: OnDataLoadCallback<Product>)
    }
}
