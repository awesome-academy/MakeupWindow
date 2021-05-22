package com.sun.makeupwindow.data.model

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcelable
import com.sun.makeupwindow.utlis.mapToListObject
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class Product(
    val id: Int,
    val brand: String,
    val name: String,
    val price: String,
    val priceSign: String,
    val currency: String,
    val imageLink: String,
    val productLink: String,
    val description: String,
    val category: String,
    val color: List<Color>
) : Parcelable {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getInt(ID),
        jsonObject.getString(BRAND),
        jsonObject.getString(NAME),
        jsonObject.getString(PRICE),
        if (jsonObject.getString(PRICESIGN) == NULL) PRICESIGN_DEFAULT else jsonObject.getString(
            PRICESIGN
        ),
        jsonObject.getString(CURRENCY),
        jsonObject.getString(IMAGELINK),
        jsonObject.getString(PRODUCTLINK),
        jsonObject.getString(DESCRIPTION),
        jsonObject.getString(PRODUCT_TYPE),
        jsonObject.getJSONArray(COLOR).mapToListObject(::Color)
    )

    constructor(curson: Cursor, listColor: List<Color>) : this(
        curson.getInt(curson.getColumnIndex(ID)),
        curson.getString(curson.getColumnIndex(BRAND)),
        curson.getString(curson.getColumnIndex(NAME)),
        curson.getString(curson.getColumnIndex(PRICE)),
        curson.getString(curson.getColumnIndex(PRICESIGN)),
        curson.getString(curson.getColumnIndex(CURRENCY)),
        curson.getString(curson.getColumnIndex(IMAGELINK)),
        curson.getString(curson.getColumnIndex(PRODUCTLINK)),
        curson.getString(curson.getColumnIndex(DESCRIPTION)),
        curson.getString(curson.getColumnIndex(PRODUCT_TYPE)),
        listColor
    )

    fun getContentValues() = ContentValues().apply {
        put(ID, id)
        put(BRAND, brand)
        put(NAME, name)
        put(PRICE, price)
        put(PRICESIGN, priceSign)
        put(CURRENCY, currency)
        put(IMAGELINK, imageLink)
        put(PRODUCTLINK, productLink)
        put(DESCRIPTION, description)
        put(PRODUCT_TYPE, category)
    }

    companion object {
        const val ID = "id"
        const val BRAND = "brand"
        const val NAME = "name"
        const val PRICE = "price"
        const val PRICESIGN = "price_sign"
        const val CURRENCY = "currency"
        const val IMAGELINK = "image_link"
        const val PRODUCTLINK = "product_link"
        const val DESCRIPTION = "description"
        const val PRODUCT_TYPE = "product_type"
        const val COLOR = "product_colors"
        const val TABLE_NAME = "table_product"
        const val TABLE_FAVORITE_NAME = "table_favorite"
        const val ID_FAVORITE_PRODUCT = "id_favorite_product"
        const val NULL = "null"
        const val PRICESIGN_DEFAULT = "$"
    }
}
