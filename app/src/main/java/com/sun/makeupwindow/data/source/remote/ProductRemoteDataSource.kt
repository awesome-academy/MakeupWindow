package com.sun.makeupwindow.data.source.remote

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.ProductDataSource
import com.sun.makeupwindow.data.source.local.utils.makeNetworkCall
import com.sun.makeupwindow.data.source.remote.api.APIQueries
import com.sun.makeupwindow.data.source.remote.utils.RemoteAsyncTask
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback
import org.json.JSONArray
import org.json.JSONObject

class ProductRemoteDataSource private constructor() : ProductDataSource.Remote {

    override fun getProductFirst(callback: OnDataLoadCallback<List<Product>>) {
        RemoteAsyncTask(callback) {
            getProductListFirst()
        }.execute()
    }

    private fun getProductListFirst(): List<Product> {
        var products = mutableListOf<Product>()
        val jsonString = makeNetworkCall(
            APIQueries.queryAllProductFirst()
        )

        val ja = JSONArray(jsonString)
        for (i in 0 until ja.length()) {
            var product = Product(ja.getJSONObject(i))
            if (product.price != ZERO)
                products.add(product)
        }
        return products.reversed()
    }

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
            var product = Product(ja.getJSONObject(i))
            if (product.price != ZERO)
                products.add(product)
        }
        return products.reversed()
    }

    override fun getProductById(id: Int, callback: OnDataLoadCallback<Product>) {
        RemoteAsyncTask(callback) {
            getProductId(id)
        }.execute()
    }

    fun getProductId(idProduct: Int): Product {
        val jsonString = makeNetworkCall(
            APIQueries.queryProduct(idProduct)
        )
        return Product(JSONObject(jsonString))
    }

    companion object {
        private const val ZERO = "0.0"
        private var instance: ProductRemoteDataSource? = null

        fun getInstance() = instance ?: ProductRemoteDataSource().also { instance = it }
    }
}
