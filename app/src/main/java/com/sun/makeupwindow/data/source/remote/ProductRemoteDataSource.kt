package com.sun.makeupwindow.data.source.remote

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.ProductDataSource
import com.sun.makeupwindow.data.source.local.ProductLocalDataSource
import com.sun.makeupwindow.data.source.local.dao.ProductDao
import com.sun.makeupwindow.data.source.local.utils.makeNetworkCall
import com.sun.makeupwindow.data.source.remote.api.APIQueries
import com.sun.makeupwindow.data.source.remote.utils.RemoteAsyncTask
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback
import org.json.JSONArray

class ProductRemoteDataSource private constructor() : ProductDataSource.Remote {

    override fun getProduct(callback: OnDataLoadCallback<List<Product>>) {
        RemoteAsyncTask(callback) {
            getProductList()
        }.execute()
    }

    private fun getProductList(): List<Product> {
        var products = mutableListOf<Product>()
        val jsonString = makeNetworkCall(
            APIQueries.queryAllProduct()
        )

        val ja = JSONArray(jsonString)
        for (i in 0 until ja.length()) {
            products.add(Product(ja.getJSONObject(i)))
        }
        return products
    }

    override fun getProductById(idProduct: Int, callback: OnDataLoadCallback<Product>) {
        RemoteAsyncTask(callback) {
            getProduct(idProduct)
        }.execute()
    }

    fun getProduct(idProduct: Int): Product {
        val jsonString = makeNetworkCall(
            APIQueries.queryProduct(idProduct)
        )
        return Product(JSONArray(jsonString).getJSONObject(0))
    }

    companion object {
        private var instance: ProductRemoteDataSource? = null

        fun getInstance() = instance ?: ProductRemoteDataSource().also { instance = it }
    }
}
