package com.sun.makeupwindow.ui.splash

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sun.makeupwindow.data.source.local.db.AppDatabase
import com.sun.makeupwindow.ui.main.MainActivity
import java.io.FileOutputStream

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(MainActivity.getIntent(applicationContext))
        finish()
    }
}
