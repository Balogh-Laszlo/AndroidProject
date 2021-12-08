package com.example.marketplace.api

import android.provider.SyncStateContract
import com.example.marketplace.model.*
import com.example.marketplace.utils.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String, @Header("limit")limit:Int): ProductResponse

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET(Constants.REFRESH_TOKEN_URL)
    suspend fun refreshToken(@Header("token") token:String) :RefreshTokenResponse

    @POST(Constants.RESET_PASSWORD)
    suspend fun resetPassword(@Body request:ResetPasswordRequest) :ResetPasswordResponse

    @POST(Constants.ADD_PRODUCT_URL)
    suspend fun addProduct(@Body request: AddProductRequest,@Header("token") token:String):Product

}