package com.example.marketplace.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.model.Reset
import com.example.marketplace.model.ResetPasswordRequest
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class ResetPasswordViewModel(private val context: Context, private val repository: Repository):ViewModel() {
    val code = MutableLiveData<Int>()
    var message =""
    var reset = MutableLiveData<Reset>()

    init{
        reset.value = Reset()
    }

    fun resetPassword(){
        viewModelScope.launch {
            try {
                val response = repository.resetPassword(ResetPasswordRequest(username=reset.value!!.username, email=reset.value!!.email))
                code.value = response.code
                message = response.message
            }
            catch (e:Exception){
                Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
            }
        }
    }
}