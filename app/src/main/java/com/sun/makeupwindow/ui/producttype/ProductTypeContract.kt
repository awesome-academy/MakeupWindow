package com.sun.makeupwindow.ui.producttype

import com.sun.makeupwindow.base.BasePresenter
import com.sun.makeupwindow.base.BaseView
import com.sun.makeupwindow.data.model.Product

interface ProductTypeContract {
    interface View : BaseView {
        fun showProducts(products: List<Product>)
    }

    interface Presenter : BasePresenter {
        fun getProducts(productType: String)
    }
}
