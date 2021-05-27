package com.sun.makeupwindow.ui.home

import com.sun.makeupwindow.data.model.Categories
import com.sun.makeupwindow.data.model.Category
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.repository.ProductRepository
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback
import com.sun.makeupwindow.utlis.*
import java.lang.Exception

class HomePresenter(
    private val view: HomeContract.View,
    private val productRepository: ProductRepository
) : HomeContract.Presenter {

    private fun checkNewData() {
        productRepository?.getLastId(object : OnDataLoadCallback<Int> {
            override fun onSuccess(data: Int) {
                if (data == ZERO) getDataFirst()
                else {
                    getDataLocal()
                    checkItemLastUpdate(data)
                }
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
                view.hideLoading()
            }
        })
    }

    private fun getDataFirst() {
        productRepository?.getFirstProduct(object : OnDataLoadCallback<List<Product>> {
            override fun onSuccess(data: List<Product>) {
                view.showProducts(data)
                view.hideLoading()
                insertData(data)
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
                view.hideLoading()
            }
        })
    }

    private fun checkItemLastUpdate(data: Int) {
        productRepository?.getProductById(data + NUMBERONE, object : OnDataLoadCallback<Product> {
            override fun onSuccess(data: Product) {
                setupData()
            }

            override fun onFail(exception: Exception) {
            }
        })
    }

    private fun getDataLocal() {
        productRepository?.getProducts(object : OnDataLoadCallback<List<Product>> {
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

    private fun setupData() {
        productRepository?.getProduct(object : OnDataLoadCallback<List<Product>> {
            override fun onSuccess(data: List<Product>) {
                view.hideLoading()
                insertData(data)
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
                view.hideLoading()
            }
        })
    }

    private fun insertData(data: List<Product>) {
        data.forEach {
            productRepository?.addProduct(it, object : OnDataLoadCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
                }

                override fun onFail(exception: Exception) {
                }
            })
        }
    }

    override fun getProducts() {
        view.showLoading()
        checkNewData()
    }

    override fun getProductLoadMore(totalItem: Int) {
        productRepository?.getProductsLoadMore(totalItem,
            object : OnDataLoadCallback<List<Product>> {
                override fun onSuccess(data: List<Product>) {
                    view.showProductsLoadMore(data)
                }

                override fun onFail(exception: Exception) {
                }
            })
    }

    override fun getCategories() {
        var listCategory = mutableListOf<Category>()
        Categories.values().map {
            listCategory.add(Category(it.image, it.name))
        }
        view.showCategories(listCategory)
    }

    override fun start() {
        getProducts()
        getCategories()
    }
}
