package com.sun.makeupwindow.ui.home

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseAdapter
import com.sun.makeupwindow.base.BaseViewHolder
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.utlis.VIEW_TYPE_ITEM
import kotlinx.android.synthetic.main.item_loading.view.*

class ProductAdapter(
    private val onItemClick: (Product) -> Unit
) : BaseAdapter<Product>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Product> {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            ProductViewHolder(view, onItemClick)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                view.progressbar.indeterminateDrawable.colorFilter =
                    BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
            } else {
                view.progressbar.indeterminateDrawable.setColorFilter(
                    Color.WHITE,
                    PorterDuff.Mode.MULTIPLY
                )
            }
            LoadingViewHolder(view)
        }
    }
}
