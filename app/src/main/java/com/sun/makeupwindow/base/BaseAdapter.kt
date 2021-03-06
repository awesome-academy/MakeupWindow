package com.sun.makeupwindow.base

import androidx.recyclerview.widget.RecyclerView
import com.sun.makeupwindow.utlis.VIEW_TYPE_ITEM
import com.sun.makeupwindow.utlis.VIEW_TYPE_LOADING

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private val items = mutableListOf<T?>()

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ITEM)
            items[position]?.let { holder.onBind(it) }
    }

    override fun getItemCount(): Int = items.size

    fun getItemAtPosition(position: Int) = items[position]

    fun addLoadingView() {
        items.add(null)
        notifyItemInserted(items.size - 1)
    }

    fun removeLoadingView() {
        if (items.size != 0) {
            items.removeAt(items.size - 1)
            notifyItemRemoved(items.size)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    fun replaceData(collection: List<T>?) {
        collection?.let {
            items.clear()
            items.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun insertData(data: T, position: Int) {
        items.add(data)
        notifyItemInserted(position)
    }

    fun removeData(data: T, position: Int) {
        items.remove(data)
        notifyItemRemoved(position)
    }

    fun removeDataAtPosition(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun reloadItem(position: Int) {
        notifyItemChanged(position)
    }
}
