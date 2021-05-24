package com.sun.makeupwindow.ui.productdetail

import android.view.View
import com.sun.makeupwindow.base.BaseViewHolder
import com.sun.makeupwindow.data.model.Color
import kotlinx.android.synthetic.main.item_product_color.view.*

class ProductColorViewHolder(
    private val itemView: View,
) : BaseViewHolder<Color>(itemView) {
    private var itemData: Color? = null

    override fun onBind(item: Color) {
        itemData = item
        itemView.run {
            imageColor.setBackgroundColor(android.graphics.Color.parseColor(item.hexValue))
            textColorName.text = item.colourName
        }
    }
}
