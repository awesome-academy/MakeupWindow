package com.sun.makeupwindow.data.repository

import com.sun.makeupwindow.data.FavoriteProductDataSource
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.ProductDataSource
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

class FavoriteProductRepository private constructor(
    private val local: FavoriteProductDataSource
) : FavoriteProductDataSource {
    override fun getFavoriteProduct(callback: OnDataLoadCallback<List<Product>>) {
        return local.getFavoriteProduct(callback)
    }

    override fun insertFavoriteProduct(idProduct: Int, callback: OnDataLoadCallback<Boolean>) {
        return local.insertFavoriteProduct(idProduct, callback)
    }

    override fun deleteFavoriteProduct(idProduct: Int, callback: OnDataLoadCallback<Boolean>) {
        return local.deleteFavoriteProduct(idProduct, callback)
    }

    companion object {
        private var instance: FavoriteProductRepository? = null

        fun getInstance(local: FavoriteProductDataSource) =
            instance ?: FavoriteProductRepository(local).also {
                instance = it
            }
    }
}
