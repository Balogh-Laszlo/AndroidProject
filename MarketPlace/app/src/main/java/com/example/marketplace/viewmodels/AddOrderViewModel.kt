package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplace.MyApplication.Companion.token
import com.example.marketplace.model.OrderRequest
import com.example.marketplace.model.OrderResponse
import com.example.marketplace.repository.Repository

class AddOrderViewModel(private val repository: Repository): ViewModel(){
    var request: OrderRequest = OrderRequest()
    val response = MutableLiveData<OrderResponse>()
    suspend fun order(){
        try {
            response.value = repository.addOrder(request,token)
        }
        catch (e:Exception){
            Log.d("ADDORDER",e.toString())
        }
    }
}