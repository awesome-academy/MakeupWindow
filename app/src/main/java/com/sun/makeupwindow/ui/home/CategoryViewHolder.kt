package com.sun.makeupwindow.ui.home

import android.view.View
import com.sun.makeupwindow.base.BaseViewHolder
import com.sun.makeupwindow.data.model.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryViewHolder(
    private val itemView: View,
    onItemClick: (Category) -> Unit
) : BaseViewHolder<Category>(itemView) {
    private var itemData: Category? = null

    init {
        itemView.setOnClickListener {
            itemData?.let {
                onItemClick(it)
            }
        }
    }

    override fun onBind(item: Category) {
        itemData = item
        itemView.run {
            item.imageCategory?.let {
                imageCategory.setImageResource(it)
            }
            textCategoryName.text = item.name
        }
    }
}
