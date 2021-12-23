package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplace.model.GetUserInfoResponse
import com.example.marketplace.repository.Repository

class GetUserInfoViewModel(private val repository: Repository): ViewModel() {
    val response = MutableLiveData<GetUserInfoResponse>()

    suspend fun getUserInfo(username:String){
        try {
            response.value = repository.getUserInfo(username)
        }
        catch (e:Exception){
            Log.d("xxx",e.toString())
        }

    }

}