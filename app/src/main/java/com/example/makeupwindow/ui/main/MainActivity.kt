package com.example.makeupwindow.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.makeupwindow.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        const val REQUEST_CODE = 1

        fun getIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java)

    }
}
