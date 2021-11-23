package com.example.marketplace.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.repository.Repository

class RefreshTokenViewModelFactory( private val repository: Repository, private val token:String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RefreshTokenViewModel(repository,token) as T
    }
}