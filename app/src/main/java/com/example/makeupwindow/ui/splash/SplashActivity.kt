package com.example.makeupwindow.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.makeupwindow.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(MainActivity.getIntent(this))
        finish()
    }
}
