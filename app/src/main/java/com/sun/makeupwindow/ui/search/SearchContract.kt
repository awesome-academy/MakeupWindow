package com.sun.makeupwindow.ui.search

import com.sun.makeupwindow.base.BasePresenter
import com.sun.makeupwindow.base.BaseView
import com.sun.makeupwindow.data.model.Product

interface SearchContract {
    interface View : BaseView {
        fun showProducts(products: List<Product>)
        fun showProductsLoadMore(products: List<Product>)
        fun showTotalProduct(totalItem: Int)
    }

    interface Presenter : BasePresenter {
        fun getProducts(searchWord: String)
        fun getProductsLoadMore(search: Map<String,Int>)
        fun getTotalProduct(searchWord: String)
    }
}

