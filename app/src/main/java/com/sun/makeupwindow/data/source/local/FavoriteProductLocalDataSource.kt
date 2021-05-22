package com.sun.makeupwindow.data.source.local

import com.sun.makeupwindow.data.FavoriteProductDataSource
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.local.dao.FavoriteProductDao
import com.sun.makeupwindow.data.source.local.utils.LocalAsyncTask
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

class FavoriteProductLocalDataSource private constructor(
    private val favoriteProductDao: FavoriteProductDao
) : FavoriteProductDataSource {

    override fun getProducts(callback: OnDataLoadCallback<List<Product>>) {
        LocalAsyncTask<Unit, List<Product>>(callback) {
            favoriteProductDao.getProducts()
        }.execute(Unit)
    }

    override fun checkProduct(id: Int, callback: OnDataLoadCallback<Boolean>) {
        LocalAsyncTask<Int, Boolean>(callback) {
            favoriteProductDao.checkProduct(id)
        }.execute(id)
    }

    override fun insertProduct(id: Int, callback: OnDataLoadCallback<Boolean>) {
        LocalAsyncTask<Int, Boolean>(callback) {
            favoriteProductDao.insertProduct(id)
        }.execute(id)
    }

    override fun deleteProduct(id: Int, callback: OnDataLoadCallback<Boolean>) {
        LocalAsyncTask<Int, Boolean>(callback) {
            favoriteProductDao.deleteProduct(id)
        }.execute(id)
    }

    companion object {
        private var instance: FavoriteProductLocalDataSource? = null

        fun getInstance(favoriteProductDao: FavoriteProductDao) =
            instance ?: FavoriteProductLocalDataSource(favoriteProductDao).also { instance = it }
    }
}
