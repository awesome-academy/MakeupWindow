package com.sun.makeupwindow.ui.search

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.repository.ProductRepository
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback
import java.lang.Exception

class SearchPresenter(
    private val view: SearchContract.View,
    private val productRepository: ProductRepository
) : SearchContract.Presenter {

    override fun getProducts(searchWord: String) {
        view.showLoading()
        productRepository.searchProducts(searchWord, object : OnDataLoadCallback<List<Product>> {
            override fun onSuccess(data: List<Product>) {
                view.showProducts(data)
                view.hideLoading()
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
                view.hideLoading()
            }
        })
    }

    override fun getProductsLoadMore(search: Map<String, Int>) {
        productRepository.searchProductLoadMore(search, object : OnDataLoadCallback<List<Product>> {
            override fun onSuccess(data: List<Product>) {
                view.showProductsLoadMore(data)
            }

            override fun onFail(exception: Exception) {
            }
        })
    }

    override fun getTotalProduct(searchWord: String) {
        view.showLoading()
        productRepository.numberOfProducts(searchWord, object : OnDataLoadCallback<Int> {
            override fun onSuccess(data: Int) {
                view.showTotalProduct(data)
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
                view.hideLoading()
            }
        })
    }

    override fun start() {

    }
}
