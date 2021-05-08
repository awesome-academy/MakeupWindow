package com.example.makeupwindow.utlis

import org.json.JSONArray
import org.json.JSONObject

inline fun <T> JSONArray.mapToListObject(parser: (JSONObject) -> T): MutableList<T> =
    mutableListOf<T>().also { list ->
        repeat(length()) {
            (get(it) as JSONObject).let(parser).let(list::add)
        }
    }
