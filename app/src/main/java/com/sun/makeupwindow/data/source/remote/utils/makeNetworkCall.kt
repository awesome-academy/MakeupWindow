package com.sun.makeupwindow.data.source.local.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun makeNetworkCall(urlLink: String): String{
    var result = ""
    var urlConnection: HttpURLConnection? = null
    try {
        val url = URL(urlLink)
        urlConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader = InputStreamReader(urlConnection.inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var line: String? = bufferedReader.readLine()
        while (line != null) {
            result = result + line + "\n"
            line = bufferedReader.readLine()
        }
    } catch (e: IOException) {
        throw IOException(urlConnection?.responseMessage)
    }
    return result
}
