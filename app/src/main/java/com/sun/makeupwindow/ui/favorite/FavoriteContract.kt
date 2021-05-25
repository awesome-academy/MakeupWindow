package com.sun.makeupwindow.ui.favorite

import com.sun.makeupwindow.base.BasePresenter
import com.sun.makeupwindow.base.BaseView
import com.sun.makeupwindow.data.model.Product

interface FavoriteContract {
    interface View : BaseView {
        fun showProducts(products: List<Product>)
    }

    interface Presenter : BasePresenter {
        fun getProducts()
    }
}
