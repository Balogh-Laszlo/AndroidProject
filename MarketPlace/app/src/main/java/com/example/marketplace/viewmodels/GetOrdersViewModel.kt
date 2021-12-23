package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplace.MyApplication
import com.example.marketplace.model.GetOrderResponse
import com.example.marketplace.repository.Repository
import org.json.JSONObject

class GetOrdersViewModel(private val repository: Repository): ViewModel() {
    var filter = MutableLiveData<Pair<String,String>>()
    val response = MutableLiveData<GetOrderResponse>()
    suspend fun getOrders(){
        try {
            val filterType = filter.value!!.first
            val filterValue = filter.value!!.second
            val filter = JSONObject("{\"$filterType\": \"$filterValue\"}").toString()
            response.value = repository.getOrders(filter,MyApplication.token)
        }
        catch (e: Exception) {
            Log.d("xxx",e.toString())
        }
    }
}