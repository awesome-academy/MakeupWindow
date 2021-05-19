package com.sun.makeupwindow.base

interface BaseView {
    fun showError(message: String)
    fun showLoading()
    fun hideLoading()
}
