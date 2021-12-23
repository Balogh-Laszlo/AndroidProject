package com.example.marketplace.repository

import com.example.marketplace.MyApplication
import com.example.marketplace.api.RetrofitInstance
import com.example.marketplace.model.*
import org.json.JSONArray
import org.json.JSONObject

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
        return RetrofitInstance.api.addProduct(token,request.title,request.description,request.price_per_unit,request.units,true,request.rating,request.amount_type,request.price_type)
    }
    suspend fun deleteProduct(product_id:String, token:String):DeleteResponse{
        return RetrofitInstance.api.deleteProduct(token,product_id)
    }
    suspend fun addOrder(orderRequest: OrderRequest, token: String): OrderResponse{
        return RetrofitInstance.api.addOrder(token,orderRequest.title,orderRequest.description,orderRequest.perice_per_unit,orderRequest.units,orderRequest.owner_username)
    }
    suspend fun getOrders(filter: String,token: String): GetOrderResponse{
        return RetrofitInstance.api.getOrders(token,1000,filter)
    }
    suspend fun updateSale(token:String, order_id:String, status:String): UpdateSaleResponse{
        return RetrofitInstance.api.updateSale(token, order_id, status)
    }
    suspend fun getUserInfo(username:String): GetUserInfoResponse{
        return RetrofitInstance.api.getUserInfo(username)
    }
    suspend fun updateUserData(request:UpdateUserDataRequest) : UpdateUserDataResponse{
        return RetrofitInstance.api.updateUserData(MyApplication.token,request)
    }

}