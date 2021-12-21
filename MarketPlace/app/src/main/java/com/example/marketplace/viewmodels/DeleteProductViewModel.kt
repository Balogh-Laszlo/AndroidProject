package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplace.model.DeleteRequest
import com.example.marketplace.model.DeleteResponse
import com.example.marketplace.repository.Repository
import java.lang.Exception

class DeleteProductViewModel(private val repository: Repository): ViewModel() {
    val deleteProductResponse = MutableLiveData<DeleteResponse>()
    val deleteRequest = MutableLiveData<DeleteRequest>()

    suspend fun deleteProduct(){
        try{
            deleteProductResponse.value = repository.deleteProduct(
                deleteRequest.value!!.product_id,
                deleteRequest.value!!.token)
        }
        catch (e: Exception){
            Log.d("xxx", "DeleteProductViewModel exception: $e")
        }
    }

}