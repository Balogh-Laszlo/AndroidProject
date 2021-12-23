package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplace.model.UpdateUserDataRequest
import com.example.marketplace.model.UpdateUserDataResponse
import com.example.marketplace.repository.Repository

class UpdateUserDataViewModel(private val repository: Repository): ViewModel(){
    val response = MutableLiveData<UpdateUserDataResponse>()

    suspend fun updateUserData(request: UpdateUserDataRequest){
        try {
            response.value = repository.updateUserData(request)
        }
        catch (e:Exception){
            Log.d("xxx", e.toString())
        }

    }
}
