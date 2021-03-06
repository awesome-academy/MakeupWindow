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

    override fun getProductsLoadMore(totalItem: Int, callback: OnDataLoadCallback<List<Product>>) {
        local.getProductsLoadMore(totalItem, callback)
    }

    override fun getLastId(callback: OnDataLoadCallback<Int>) {
        local.getLastId(callback)
    }

    override fun getProductId(id: Int, callback: OnDataLoadCallback<Product>) =
        local.getProductId(id, callback)


    override fun getProductsByCategory(
        category: String,
        callback: OnDataLoadCallback<List<Product>>
    ) {
        return local.getProductsByCategory(category, callback)
    }

    override fun addProduct(product: Product, callback: OnDataLoadCallback<Boolean>) {
        return local.addProduct(product, callback)
    }

    override fun searchProducts(
        wordSearch: String,
        callback: OnDataLoadCallback<List<Product>>
    ) {
        return local.searchProducts(wordSearch, callback)
    }

    override fun searchProductLoadMore(
        search: Map<String, Int>,
        callback: OnDataLoadCallback<List<Product>>
    ) {
        return local.searchProductLoadMore(search, callback)
    }

    override fun numberOfProducts(wordSearch: String, callback: OnDataLoadCallback<Int>) {
        return local.numberOfProducts(wordSearch, callback)
    }

    override fun getFirstProduct(callback: OnDataLoadCallback<List<Product>>) =
        remote.getFirstProduct(callback)

    override fun getProduct(callback: OnDataLoadCallback<List<Product>>) {
        remote.getProduct(callback)
    }

    override fun getProductById(id: Int, callback: OnDataLoadCallback<Product>) =
        remote.getProductById(id, callback)

    companion object {
        private var instance: ProductRepository? = null

        fun getInstance(
            local: ProductDataSource.Local,
            remote: ProductDataSource.Remote
        ) = instance ?: ProductRepository(local, remote).also { instance = it }
    }
}
