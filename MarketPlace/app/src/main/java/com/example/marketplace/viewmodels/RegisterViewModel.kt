package com.example.marketplace.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplace.model.RegisterRequest
import com.example.marketplace.model.User
import com.example.marketplace.repository.Repository

class RegisterViewModel(val context: Context, private val repository: Repository) : ViewModel() {
    var user = MutableLiveData<User>()
    var code = MutableLiveData<Int>()

    init {
        user.value = User()
    }
    suspend fun register() {
        val request = RegisterRequest(username = user.value!!.username, password = user.value!!.password, email = user.value!!.email, phone_number = user.value!!.phone_number)
        try {
            val result = repository.register(request)
            code.value = result.code

        }catch (e: Exception) {
            Log.d("xxx", "RegisterViewModel - exception: $e")
        }
    }
}