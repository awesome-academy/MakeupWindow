package com.sun.makeupwindow.ui.home

import com.sun.makeupwindow.base.BasePresenter
import com.sun.makeupwindow.base.BaseView
import com.sun.makeupwindow.data.model.Category
import com.sun.makeupwindow.data.model.Product

interface HomeContract {
    interface View : BaseView {
        fun showProducts(products: List<Product>)
        fun showCategories(categories: List<Category>)
    }

    interface Presenter : BasePresenter {
        fun getProducts()
        fun getCategories()
    }
}
