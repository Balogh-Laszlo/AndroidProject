package com.example.marketplace.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.repository.Repository

class GetOrdersViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GetOrdersViewModel(repository) as T
    }
}