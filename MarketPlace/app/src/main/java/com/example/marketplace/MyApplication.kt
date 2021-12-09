package com.example.marketplace

import android.annotation.SuppressLint
import android.app.Application
import android.view.MenuItem
import android.widget.SearchView

class MyApplication: Application(){
    companion object{
        var token: String =""
        var username: String =""
        @SuppressLint("StaticFieldLeak")
        var searchView:SearchView? = null
        var searchMenuItem: MenuItem? = null
    }
}