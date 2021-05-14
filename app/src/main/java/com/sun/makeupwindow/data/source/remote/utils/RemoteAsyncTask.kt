package com.sun.makeupwindow.data.source.remote.utils

import android.os.AsyncTask
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback

class RemoteAsyncTask<T>(
    private val callback: OnDataLoadCallback<T>,
    private val handle: () -> T
) : AsyncTask<Unit, Unit, T?>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg params: Unit?): T? =
        try {
            handle()
        } catch (e: Exception) {
            exception = e
            null
        }

    override fun onPostExecute(result: T?) {
        result?.let(callback::onSuccess) ?: (exception?.let { callback.onFail(it) })
    }
}
