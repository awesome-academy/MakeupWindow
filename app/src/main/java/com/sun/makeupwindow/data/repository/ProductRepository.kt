package com.sun.makeupwindow.data.repository

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.source.ProductDataSource
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

class ProductRepository private constructor(
    private val local: ProductDataSource.Local,
    private val remote: ProductDataSource.Remote
) : ProductDataSource.Local, ProductDataSource.Remote {

    override fun getProducts(callback: OnDataLoadCallback<List<Product>>) =
        local.getProducts(callback)

    override fun getItemProduct(idProduct: Int, callback: OnDataLoadCallback<Product>) =
        local.getItemProduct(idProduct, callback)


    override fun getProductsByCategory(
        category: String,
        callback: OnDataLoadCallback<List<Product>>
    ) {
        return local.getProductsByCategory(category, callback)
    }

    override fun addProduct(product: Product, callback: OnDataLoadCallback<Boolean>) {
        return local.addProduct(product, callback)
    }

    override fun getProduct(callback: OnDataLoadCallback<List<Product>>) =
        remote.getProduct(callback)

    override fun getProductById(idProduct: Int, callback: OnDataLoadCallback<Product>) =
        remote.getProductById(idProduct, callback)

    companion object {
        private var instance: ProductRepository? = null

        fun getInstance(
            local: ProductDataSource.Local,
            remote: ProductDataSource.Remote
        ) = instance ?: ProductRepository(local, remote).also { instance = it }
    }
}
