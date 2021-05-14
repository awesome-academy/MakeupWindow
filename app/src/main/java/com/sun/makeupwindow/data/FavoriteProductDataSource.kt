package com.sun.makeupwindow.data

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

interface FavoriteProductDataSource {
    fun getFavoriteProduct(callback: OnDataLoadCallback<List<Product>>)
    fun insertFavoriteProduct(idProduct: Int, callback: OnDataLoadCallback<Boolean>)
    fun deleteFavoriteProduct(idProduct: Int, callback: OnDataLoadCallback<Boolean>)
}
