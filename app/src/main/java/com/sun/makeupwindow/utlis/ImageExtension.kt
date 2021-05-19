package com.sun.makeupwindow.utlis

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sun.makeupwindow.R

fun ImageView.loadImage(image: String) {
    Glide.with(context)
        .load(image)
        .error(R.drawable.ic_broken_image)
        .placeholder(R.drawable.ic_place_holder)
        .into(this)
}

