package com.sun.makeupwindow.data.source.local.dao

import com.sun.makeupwindow.data.model.Color
import com.sun.makeupwindow.data.source.local.db.AppDatabase

class ColorDaoImpl private constructor(
    appDatabase: AppDatabase
) : ColorDao {

    private val writableDatabase = appDatabase.writableDatabase
    private val readableDatabase = appDatabase.readableDatabase

    override fun getColor(id: Int): List<Color> {
        val listColor = mutableListOf<Color>()
        val cursor = readableDatabase.query(
            Color.TABLE_NAME, null, Color.IDPRODUCT + " = ?",
            arrayOf(id.toString()), null, null, null, null
        )
        cursor.use {
            it.moveToFirst()
            while (!it.isAfterLast) {
                listColor.add(Color(it))
                it.moveToNext()
            }
        }
        return listColor
    }

    override fun addColor(listColor: List<Color>, idProduct: Int): Boolean {
        var count = 0
        listColor.forEach {
            if (writableDatabase.insert(
                    Color.TABLE_NAME,
                    null,
                    it.getContentValues(idProduct)
                ) > 0
            )
                count++
        }
        return count == listColor.size + 1
    }

    companion object {
        private var instance: ColorDaoImpl? = null

        fun getInstance(database: AppDatabase): ColorDaoImpl =
            instance ?: ColorDaoImpl(database).also {
                instance = it
            }
    }
}
