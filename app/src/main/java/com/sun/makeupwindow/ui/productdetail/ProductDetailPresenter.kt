package com.sun.makeupwindow.ui.productdetail

import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.repository.FavoriteProductRepository
import com.sun.makeupwindow.data.repository.ProductRepository
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback
import java.lang.Exception

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
    private val favoriteProductRepository: FavoriteProductRepository
) : ProductDetailContract.Presenter {

    override fun getRelatedProducts(productType: String) {
        view.showLoading()
        productRepository.getProductsByCategory(
            productType,
            object : OnDataLoadCallback<List<Product>> {
                override fun onSuccess(data: List<Product>) {
                    view.showRelatedProducts(data)
                    view.hideLoading()
                }

                override fun onFail(exception: Exception) {
                    view.showError(exception.message.toString())
                    view.hideLoading()
                }
            })
    }

    override fun getProductFavorite(id: Int) {
        view.showLoading()
        favoriteProductRepository.checkProduct(id, object : OnDataLoadCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                view.isFavoriteProduct(data)
                view.hideLoading()
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
                view.hideLoading()
            }
        })
    }

    override fun insertFavorite(id: Int) {
        favoriteProductRepository.insertProduct(id, object : OnDataLoadCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                view.isInsertedFavoriteProduct(data)
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
            }
        })
    }

    override fun deleteFavorite(id: Int) {
        favoriteProductRepository.deleteProduct(id, object : OnDataLoadCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                view.isDeletedFavoriteProduct(data)
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
            }
        })
    }

    override fun start() {
    }
}
