package com.sun.makeupwindow.ui.search

import android.view.View
import com.sun.makeupwindow.base.BaseViewHolder
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.utlis.loadImage
import kotlinx.android.synthetic.main.item_result.view.*

class SearchViewHolder (
    private val itemView: View,
    onItemClick: (Product) -> Unit
) : BaseViewHolder<Product>(itemView) {

    private var itemData: Product? = null

    init {
        itemView.setOnClickListener {
            itemData?.let {
                onItemClick(it)
            }
        }
    }

    override fun onBind(item: Product) {
        itemData = item
        itemView.run {
            item.imageLink?.let {
                imageResult.loadImage(it)
            }
            textResultName.text = item.name
        }
    }
}
