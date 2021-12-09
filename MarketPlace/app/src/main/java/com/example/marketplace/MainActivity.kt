package com.example.marketplace


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu

import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
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
        Log.d("xxx","OnCreate MainActivity")
        MyApplication.searchView = searchView
        MyApplication.searchMenuItem = myActionMenuItem
    }
}