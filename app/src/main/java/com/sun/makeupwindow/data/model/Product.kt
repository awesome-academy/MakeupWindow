package com.sun.makeupwindow.data.model

import android.os.Parcelable
import com.sun.makeupwindow.utlis.mapToListObject
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class Product(
    val id: Int,
    val brand: String,
    val name: String,
    val price: Double,
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
        jsonObject.getDouble(PRICE),
        jsonObject.getString(PRICESIGN),
        jsonObject.getString(CURRENCY),
        jsonObject.getString(IMAGELINK),
        jsonObject.getString(PRODUCTLINK),
        jsonObject.getString(DESCRIPTION),
        jsonObject.getString(CATEGORY),
        jsonObject.getJSONArray(COLOR).mapToListObject (::Color)
    )

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
        const val CATEGORY = "category"
        const val COLOR = "product_colors"
    }
}
