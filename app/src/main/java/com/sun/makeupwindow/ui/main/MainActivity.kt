package com.sun.makeupwindow.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sun.makeupwindow.R
import com.sun.makeupwindow.base.BaseActivity
import com.sun.makeupwindow.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override val layoutResource: Int get() = R.layout.activity_main

    override fun initComponents() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavigation)
        bottomNavigationView.selectedItemId = R.id.menuHome
    }

    private val onBottomNavigation =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuHome -> openFragment(HomeFragment.getInstance())
            }
            true
        }

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
