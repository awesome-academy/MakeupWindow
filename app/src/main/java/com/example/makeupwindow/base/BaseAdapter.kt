package com.example.makeupwindow.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private val items = mutableListOf<T>()

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

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
