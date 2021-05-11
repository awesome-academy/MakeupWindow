package com.sun.makeupwindow.data.source.local

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.ProductDataSource
import com.sun.makeupwindow.data.source.local.dao.ProductDAO
import com.sun.makeupwindow.data.source.local.utils.LocalAsyncTask
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

@Suppress("DEPRECATION")
class ProductLocalDataSource private constructor(
    private val productDAO: ProductDAO
) : ProductDataSource.Local {

    override fun getProducts(callback: OnDataLoadCallback<List<Product>>) {
        LocalAsyncTask<Unit, List<Product>>(callback) {
            productDAO.getProduct()
        }.execute(Unit)
    }

    override fun getItemProduct(idproduct: Int, callback: OnDataLoadCallback<Product>) {
        LocalAsyncTask<Int,Product>(callback) {
            productDAO.getItemProduct(idproduct)
        }.execute(idproduct)
    }

    override fun getProductsByCategory(
        category: String,
        callback: OnDataLoadCallback<List<Product>>
    ) {
        LocalAsyncTask<String,List<Product>>(callback){
            productDAO.getProductByCategory(category)
        }.execute(category)
    }

    override fun addProduct(product: Product, callback: OnDataLoadCallback<Boolean>) {
        LocalAsyncTask<Product,Boolean>(callback){
            productDAO.addProduct(product)
        }.execute(product)
    }

    companion object {
        private var instance: ProductLocalDataSource? = null

        fun getInstance(productDAO: ProductDAO): ProductLocalDataSource =
            instance ?: synchronized(this) {
                instance ?: ProductLocalDataSource(productDAO).also {
                    instance = it
                }
            }
    }
}
