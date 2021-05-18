package com.sun.makeupwindow.data.source

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

interface ProductDataSource {
    interface Local{
        fun getProducts(callback: OnDataLoadCallback<List<Product>>)
        fun getLastId(callback: OnDataLoadCallback<Int>)
        fun getProductId(id: Int, callback: OnDataLoadCallback<Product>)
        fun getProductsByCategory(category: String, callback: OnDataLoadCallback<List<Product>>)
        fun addProduct(product: Product, callback: OnDataLoadCallback<Boolean>)
    }

    interface Remote{
        fun getProductFirst(callback: OnDataLoadCallback<List<Product>>)
        fun getProduct(callback: OnDataLoadCallback<List<Product>>)
        fun getProductById(id: Int, callback: OnDataLoadCallback<Product>)
    }
}
