package com.sun.makeupwindow.data.source.remote.api

import android.net.Uri

object APIQueries {
    fun queryAllProductFirst() =
        Uri.Builder().scheme(APIConstants.SCHEME_HTTPS)
            .authority(APIConstants.OPENMAKEUP_AUTHORITY_API_FIRST)
            .appendEncodedPath(APIConstants.PATH_ALL_PRODUCT_API_FIRST).toString()

    fun queryAllProduct() =
        Uri.Builder().scheme(APIConstants.SCHEME_HTTP)
            .authority(APIConstants.OPENMAKEUP_AUTHORITY_API)
            .appendEncodedPath(APIConstants.PATH_ALL_PRODUCT_API).toString()

    fun queryProduct(id: Int) =
        Uri.Builder().scheme(APIConstants.SCHEME_HTTP)
            .authority(APIConstants.OPENMAKEUP_AUTHORITY_API)
            .appendEncodedPath(APIConstants.PATH_PRODUCT_API)
            .appendEncodedPath(id.toString() + APIConstants.JSON).toString()
}
