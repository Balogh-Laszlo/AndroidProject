package com.example.marketplace


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton

import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.marketplace.model.Product
import com.example.marketplace.model.SharedViewModel
import com.example.marketplace.viewmodels.ListViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView




class MainActivity : AppCompatActivity() {
    companion object{
        const val EXTERNAL_PERMISSION_CODE = 440
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.nav_fragment)
        bottomNavigationView.setupWithNavController(navController)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val menu =topAppBar.menu
        val myActionMenuItem = menu.findItem(R.id.searchMenu)
        val searchView = myActionMenuItem.actionView as SearchView
        val profileMenuItem = menu.findItem(R.id.profileMenu)
        val profile = profileMenuItem.actionView as ImageButton
        Glide.with(this)
            .load(R.drawable.avdefault_avatar)
            .override(100)
            .circleCrop()
            .into(profile)
        profile.background.alpha = 0
        profile.setOnClickListener {
            findNavController(R.id.nav_fragment).navigate(R.id.ownersProfileFragment)
        }
        Log.d("xxx","OnCreate MainActivity")
        MyApplication.searchView = searchView
        MyApplication.searchMenuItem = myActionMenuItem
    }
}