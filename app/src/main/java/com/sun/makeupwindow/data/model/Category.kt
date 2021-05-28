package com.sun.makeupwindow.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val imageCategory: Int?,
    val name: String
): Parcelable

