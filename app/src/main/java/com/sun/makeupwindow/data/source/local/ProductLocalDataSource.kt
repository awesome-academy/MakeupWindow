package com.sun.makeupwindow.data.source.local

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.ProductDataSource
import com.sun.makeupwindow.data.source.local.dao.ProductDao
import com.sun.makeupwindow.data.source.local.utils.LocalAsyncTask
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

@Suppress("DEPRECATION")
class ProductLocalDataSource private constructor(
    private val productDao: ProductDao
) : ProductDataSource.Local {

    override fun getProducts(callback: OnDataLoadCallback<List<Product>>) {
        LocalAsyncTask<Unit, List<Product>>(callback) {
            productDao.getProducts()
        }.execute(Unit)
    }

    override fun getProductsLoadMore(totalItem: Int, callback: OnDataLoadCallback<List<Product>>) {
        LocalAsyncTask<Int, List<Product>>(callback) {
            productDao.getProductsLoadMore(totalItem)
        }.execute(totalItem)
    }

    override fun getLastId(callback: OnDataLoadCallback<Int>) {
        LocalAsyncTask<Unit, Int>(callback) {
            productDao.getLastId()
        }.execute(Unit)
    }

    override fun getProductId(productId: Int, callback: OnDataLoadCallback<Product>) {
        LocalAsyncTask<Int, Product>(callback) {
            productDao.getItemProduct(productId)
        }.execute(productId)
    }

    override fun getProductsByCategory(
        category: String,
        callback: OnDataLoadCallback<List<Product>>
    ) {
        LocalAsyncTask<String, List<Product>>(callback) {
            productDao.getProductByCategory(category)
        }.execute(category)
    }

    override fun addProduct(product: Product, callback: OnDataLoadCallback<Boolean>) {
        LocalAsyncTask<Product, Boolean>(callback) {
            productDao.addProduct(product)
        }.execute(product)
    }

    companion object {
        private var instance: ProductLocalDataSource? = null

        fun getInstance(productDao: ProductDao) =
            instance ?: ProductLocalDataSource(productDao).also {
                instance = it
            }
    }
}
