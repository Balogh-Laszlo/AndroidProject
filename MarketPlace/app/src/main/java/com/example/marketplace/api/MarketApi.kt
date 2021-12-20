package com.example.marketplace.api

import android.provider.SyncStateContract
import com.example.marketplace.model.*
import com.example.marketplace.utils.Constants
import retrofit2.http.*

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

    //Part
    @Multipart
    @POST(Constants.ADD_PRODUCT_URL)
    suspend fun addProduct(@Header("token") token:String,
                          @Part("title") title:String,
                          @Part("description") description:String,
                          @Part("price_per_unit") price_per_unit:String,
                          @Part("units") units:String,
                          @Part("is_active") is_active:Boolean,
                          @Part("rating") rating:Double,
                          @Part("amount_type") amount_type:String,
                          @Part("price_type") price_type:String
                    ):Product

}