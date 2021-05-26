package com.sun.makeupwindow.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseAdapter
import com.sun.makeupwindow.base.BaseViewHolder
import com.sun.makeupwindow.data.model.Product

class SearchAdapter (
    private val onItemClick: (Product) -> Unit
) : BaseAdapter<Product>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Product> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_result, parent, false)
        return SearchViewHolder(view, onItemClick)
    }
}
