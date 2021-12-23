package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplace.MyApplication
import com.example.marketplace.model.UpdateSaleResponse
import com.example.marketplace.repository.Repository
import org.json.JSONArray
import org.json.JSONObject

class UpdateSaleViewModel(private val repository:Repository): ViewModel(){
    val response = MutableLiveData<UpdateSaleResponse>()
    suspend fun updateSale(order_id:String,status:String){
        try {
            val stat = JSONObject("{\"status\": \"$status\"}").toString()
            response.value = repository.updateSale(MyApplication.token,order_id, stat)
        }catch (e:Exception){
            Log.d("xxx",e.toString())
        }
    }

}