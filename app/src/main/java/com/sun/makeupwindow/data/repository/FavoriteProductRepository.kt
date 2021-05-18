package com.sun.makeupwindow.data.repository

import com.sun.makeupwindow.data.FavoriteProductDataSource
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

class FavoriteProductRepository private constructor(
    private val local: FavoriteProductDataSource
) : FavoriteProductDataSource {
    override fun getProducts(callback: OnDataLoadCallback<List<Product>>) {
        return local.getProducts(callback)
    }

    override fun insertProduct(id: Int, callback: OnDataLoadCallback<Boolean>) {
        return local.insertProduct(id, callback)
    }

    override fun deleteProduct(id: Int, callback: OnDataLoadCallback<Boolean>) {
        return local.deleteProduct(id, callback)
    }

    companion object {
        private var instance: FavoriteProductRepository? = null

        fun getInstance(local: FavoriteProductDataSource) =
            instance ?: FavoriteProductRepository(local).also {
                instance = it
            }
    }
}
