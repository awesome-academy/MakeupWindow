package com.sun.makeupwindow.data.model

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

    companion object {
        const val HEXVALUE = "hex_value"
        const val COLOURNAME = "colour_name"
    }
}
