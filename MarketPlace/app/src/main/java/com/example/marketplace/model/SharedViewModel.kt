package com.example.marketplace.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {
        var currentProduct: Product? = null
        var productList: List<Product>? = null
        val soldItems: Int = 0
        var mySales = MutableLiveData<List<Order>>()
        var myOrders = MutableLiveData<List<Order>>()
}