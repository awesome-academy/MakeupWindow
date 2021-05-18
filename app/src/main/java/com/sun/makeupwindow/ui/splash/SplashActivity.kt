package com.sun.makeupwindow.ui.splash

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sun.makeupwindow.data.model.Product
import com.sun.makeupwindow.data.repository.ProductRepository
import com.sun.makeupwindow.data.source.utils.OnDataLoadCallback
import com.sun.makeupwindow.ui.main.MainActivity
import com.sun.makeupwindow.utlis.NUMBERONE
import com.sun.makeupwindow.utlis.RepositoryFactory
import java.lang.Exception

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(MainActivity.getIntent(applicationContext))
        finish()
    }
}
