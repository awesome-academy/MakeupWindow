package com.sun.makeupwindow.utlis

import android.content.Context
import com.sun.makeupwindow.data.repository.ProductRepository
import com.sun.makeupwindow.data.source.local.ProductLocalDataSource
import com.sun.makeupwindow.data.source.local.dao.ProductDaoImpl
import com.sun.makeupwindow.data.source.local.db.AppDatabase
import com.sun.makeupwindow.data.source.remote.ProductRemoteDataSource

object RepositoryFactory {
    fun getRepository(context: Context): ProductRepository {
        val appDatabase = AppDatabase.getInstance(context)
        val productDaoImpl = ProductDaoImpl.getInstance(appDatabase)
        val local = ProductLocalDataSource.getInstance(productDaoImpl)
        val remote = ProductRemoteDataSource.getInstance()
        return ProductRepository.getInstance(local, remote)
    }
}
