package com.sun.makeupwindow.utlis

import android.content.Context
import com.sun.makeupwindow.data.repository.FavoriteProductRepository
import com.sun.makeupwindow.data.repository.ProductRepository
import com.sun.makeupwindow.data.source.local.FavoriteProductLocalDataSource
import com.sun.makeupwindow.data.source.local.ProductLocalDataSource
import com.sun.makeupwindow.data.source.local.dao.FavoriteProductDaoImpl
import com.sun.makeupwindow.data.source.local.dao.ProductDaoImpl
import com.sun.makeupwindow.data.source.local.db.AppDatabase
import com.sun.makeupwindow.data.source.remote.ProductRemoteDataSource

object RepositoryFactory {
    fun getRepository(context: Context): ProductRepository {
        AppDatabase.copyDatabase(context)
        val appDatabase = AppDatabase.getInstance(context)
        val productDaoImpl = ProductDaoImpl.getInstance(appDatabase)
        val local = ProductLocalDataSource.getInstance(productDaoImpl)
        val remote = ProductRemoteDataSource.getInstance()
        return ProductRepository.getInstance(local, remote)
    }

    fun getRepositoryFavorite(context: Context): FavoriteProductRepository{
        val appDatabase = AppDatabase.getInstance(context)
        val favoriteProductDaoImpl = FavoriteProductDaoImpl.getInstance(appDatabase)
        val local = FavoriteProductLocalDataSource.getInstance(favoriteProductDaoImpl)
        return FavoriteProductRepository.getInstance(local)
    }
}
