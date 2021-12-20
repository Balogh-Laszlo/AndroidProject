package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.MyApplication
import com.example.marketplace.model.AddProductRequest
import com.example.marketplace.model.Product
import com.example.marketplace.model.User
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch

class AddProductViewModel(private val repository: Repository) : ViewModel() {
    val productResult= MutableLiveData<Product>()
    var product = MutableLiveData<Product>()

    init {
        product.value = Product()

    }

    suspend fun addProduct() {
        try {
            val request = AddProductRequest(uploadImages = product.value!!.images,
                                            title=product.value!!.title,
                                            amount_type = product.value!!.amount_type,
                                            description = product.value!!.description,
                                            is_active = true,
                                            price_per_unit = product.value!!.price_per_unit,
                                            price_type = product.value!!.price_type,
                                            rating= 5.0,
                                            units = product.value!!.units
                )
            productResult.value = repository.addProduct(request,MyApplication.token)

        }catch(e: Exception){
            Log.d("xxx", "AddProductViewModel exception: $e")
        }
    }
}