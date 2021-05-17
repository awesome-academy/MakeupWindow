package com.sun.makeupwindow.data.source.remote.api

import android.net.Uri

object APIQueries {
    fun queryAllProduct() =
        Uri.Builder().scheme(APIConstants.SCHEME_HTTP)
            .authority(APIConstants.OPENMAKEUP_AUTHORITY_API)
            .appendPath(APIConstants.PATH_ALL_PRODUCT_API).toString()

    fun queryProduct(id: Int) =
        Uri.Builder().scheme(APIConstants.SCHEME_HTTP)
            .authority(APIConstants.OPENMAKEUP_AUTHORITY_API)
            .appendPath(APIConstants.PATH_PRODUCT_API)
            .appendPath(id.toString() + APIConstants.JSON).toString()
}
