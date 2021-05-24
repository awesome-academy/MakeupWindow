package com.sun.makeupwindow.ui.productdetail

import com.sun.makeupwindow.base.BasePresenter
import com.sun.makeupwindow.base.BaseView
import com.sun.makeupwindow.data.model.Product

interface ProductDetailContract {
    interface View: BaseView{
        fun isFavoriteProduct(check: Boolean)
        fun showRelatedProducts(products: List<Product>)
        fun isInsertedFavoriteProduct(check: Boolean)
        fun isDeletedFavoriteProduct(check: Boolean)
    }

    interface Presenter:BasePresenter{
        fun getRelatedProducts(productType: String)
        fun getProductFavorite(id: Int)
        fun insertFavorite(id: Int)
        fun deleteFavorite(id: Int)
    }
}
