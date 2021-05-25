package com.sun.makeupwindow.ui.favorite

import android.view.View
import com.sun.makeupwindow.base.BaseViewHolder
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.utlis.loadImage
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteProductViewHolder(
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
                imageFavoriteProduct.loadImage(it)
            }
            textName.text = item.name
            textPrice.text = "${item.price} ${item.priceSign}"
        }
    }
}
