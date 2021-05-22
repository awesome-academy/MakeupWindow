package com.sun.makeupwindow.data

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

interface FavoriteProductDataSource {
    fun getProducts(callback: OnDataLoadCallback<List<Product>>)
    fun checkProduct(id: Int, callback: OnDataLoadCallback<Boolean>)
    fun insertProduct(id: Int, callback: OnDataLoadCallback<Boolean>)
    fun deleteProduct(id: Int, callback: OnDataLoadCallback<Boolean>)
}
