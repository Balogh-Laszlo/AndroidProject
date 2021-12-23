package com.example.marketplace.model

import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {
        var currentProduct: Product? = null
        var productList: List<Product>? = null
        val soldItems: Int = 0
        var mySales: List<Order> = listOf()
        var myOrders: List<Order> = listOf()
}