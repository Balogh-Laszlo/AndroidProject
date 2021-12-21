package com.example.marketplace.repository

import com.example.marketplace.api.RetrofitInstance
import com.example.marketplace.model.*

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getProducts(token: String, limit:Int): ProductResponse {
        return RetrofitInstance.api.getProducts(token,limit)
    }
    suspend fun register(request: RegisterRequest): RegisterResponse {
        return RetrofitInstance.api.register(request)
    }
    suspend fun refreshToken(token:String) : RefreshTokenResponse{
        return RetrofitInstance.api.refreshToken(token)
    }
    suspend fun resetPassword(request:ResetPasswordRequest) :ResetPasswordResponse{
        return RetrofitInstance.api.resetPassword(request)
    }
    suspend fun addProduct(request:AddProductRequest,token: String): Product{
        return RetrofitInstance.api.addProduct(token,request.title,request.description,request.price_per_unit.toString(),request.units.toString(),true,request.rating,request.amount_type,request.price_type)
    }
    suspend fun deleteProduct(product_id:String, token:String):DeleteResponse{
        return RetrofitInstance.api.deleteProduct(token,product_id)
    }

}