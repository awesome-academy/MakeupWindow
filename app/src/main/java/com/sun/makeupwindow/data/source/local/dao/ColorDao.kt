package com.sun.makeupwindow.data.source.local.dao

import com.sun.makeupwindow.data.model.Color

interface ColorDao {
    fun getColor(idProduct: Int): List<Color>
    fun addColor(colours: List<Color>, idProduct: Int): Boolean
}
