package com.example.marketplace.model

import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {
    var currentProduct:Product? = null
    var productList:List<Product>? = null
}