package com.sun.makeupwindow.ui.productdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseAdapter
import com.sun.makeupwindow.base.BaseViewHolder
import com.sun.makeupwindow.data.model.Color

class ProductColorAdapter : BaseAdapter<Color>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Color> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_color, parent, false)
        return ProductColorViewHolder(view)
    }
}
