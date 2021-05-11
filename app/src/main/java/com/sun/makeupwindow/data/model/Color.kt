package com.sun.makeupwindow.data.model

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class Color(
    val hexValue: String,
    val colourName: String
): Parcelable {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getString(HEXVALUE),
        jsonObject.getString(COLOURNAME)
    )

    constructor(cursor: Cursor) : this(
        cursor.getString(cursor.getColumnIndex(HEXVALUE)),
        cursor.getString(cursor.getColumnIndex(COLOURNAME))
    )

    fun getContentValues(idProduct: Int) = ContentValues().apply {
        put(HEXVALUE, hexValue)
        put(COLOURNAME, colourName)
        put(IDPRODUCT,idProduct)
    }

    companion object {
        const val ID = "id"
        const val HEXVALUE = "hex_value"
        const val COLOURNAME = "colour_name"
        const val IDPRODUCT = "id_product"
        const val TABLE_NAME = "product_color"
    }
}
