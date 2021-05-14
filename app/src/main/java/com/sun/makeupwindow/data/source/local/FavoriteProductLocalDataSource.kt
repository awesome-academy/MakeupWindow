package com.sun.makeupwindow.data.source.local

import com.sun.makeupwindow.data.FavoriteProductDataSource
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.local.dao.FavoriteProductDao
import com.sun.makeupwindow.data.source.local.utils.LocalAsyncTask
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

class FavoriteProductLocalDataSource private constructor(
    private val favoriteProductDao: FavoriteProductDao
) : FavoriteProductDataSource {

    override fun getFavoriteProduct(callback: OnDataLoadCallback<List<Product>>) {
        LocalAsyncTask<Unit, List<Product>>(callback) {
            favoriteProductDao.getProducts()
        }.execute(Unit)
    }

    override fun insertFavoriteProduct(productId: Int, callback: OnDataLoadCallback<Boolean>) {
        LocalAsyncTask<Int, Boolean>(callback) {
            favoriteProductDao.insertProduct(productId)
        }.execute(productId)
    }

    override fun deleteFavoriteProduct(productId: Int, callback: OnDataLoadCallback<Boolean>) {
        LocalAsyncTask<Int, Boolean>(callback) {
            favoriteProductDao.deleteProduct(productId)
        }.execute(productId)
    }

    companion object {
        private var instance: FavoriteProductLocalDataSource? = null

        fun getInstance(favoriteProductDao: FavoriteProductDao) =
            instance ?: FavoriteProductLocalDataSource(favoriteProductDao).also { instance = it }
    }
}
