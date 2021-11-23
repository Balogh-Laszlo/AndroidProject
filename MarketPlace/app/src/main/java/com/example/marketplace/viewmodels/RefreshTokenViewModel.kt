package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplace.repository.Repository
import java.lang.Exception


class RefreshTokenViewModel(private val repository: Repository, private val sharedToken:String):ViewModel() {
    var token = MutableLiveData<String>()

    suspend fun refreshToken(){
        try {
            val result = repository.refreshToken(sharedToken)
            token.value = result.token
        }
        catch (e:Exception){
            Log.d("xxx", "RefreshTokenViewModel exception: ${e.toString()}")
        }
    }
}