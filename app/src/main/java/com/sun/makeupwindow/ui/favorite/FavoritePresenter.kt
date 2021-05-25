package com.sun.makeupwindow.ui.favorite

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.repository.FavoriteProductRepository
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback
import java.lang.Exception

class FavoritePresenter (
    private val view: FavoriteContract.View,
    private val favoriteProductRepository: FavoriteProductRepository
) : FavoriteContract.Presenter {

    override fun getProducts() {
        view.showLoading()
        favoriteProductRepository.getProducts(object : OnDataLoadCallback<List<Product>> {
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

    override fun start() {
        getProducts()
    }
}
