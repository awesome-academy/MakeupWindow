package com.sun.makeupwindow.ui.producttype

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.repository.ProductRepository
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback
import java.lang.Exception

class ProductTypePresenter(
    private val view: ProductTypeContract.View,
    private val productRepository: ProductRepository
) : ProductTypeContract.Presenter {
    override fun getProducts(productType: String) {
        view.showLoading()
        productRepository.getProductsByCategory(
            productType,
            object : OnDataLoadCallback<List<Product>> {
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
    }
}
