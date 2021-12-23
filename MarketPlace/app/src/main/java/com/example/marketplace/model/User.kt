package com.example.marketplace.model

import com.squareup.moshi.JsonClass

data class User(var username: String="", var password: String="", var email: String="", var phone_number: String="")

data class Reset(var username: String="", var email: String="")

@JsonClass(generateAdapter = true)
data class LoginRequest (
    var username: String,
    var password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse (
    var username: String,
    var email: String,
    var phone_number: Int,
    var token: String,
    var creation_time: Long,
    var refresh_time: Long
)
@JsonClass(generateAdapter = true)
data class RegisterRequest(
    var username: String,
    var email: String,
    var phone_number: String,
    var password: String

)
@JsonClass(generateAdapter = true)
data class RegisterResponse(
    var code:Int,
    var message:String,
    var creation_time: Long
)
@JsonClass(generateAdapter = true)
data class RefreshTokenResponse(
    val token: String,
    val creation_time: Long,
    val refresh_time: Long
)
@JsonClass(generateAdapter = true)
data class ResetPasswordRequest(
    val username: String,
    val email: String
)
@JsonClass(generateAdapter = true)
data class ResetPasswordResponse(
    val code: Int,
    val message: String,
    val timestamp:Long
)

data class UserData(
    val username: String,
    val phone_number: String,
    val email: String,
    val is_activated:Boolean,
    val creation_time: String
)

@JsonClass(generateAdapter = true)
data class GetUserInfoResponse(
    val code: Int,
    val data: List<UserData>,
    val timestamp: Long
)

@JsonClass(generateAdapter = true)
data class UpdateUserDataRequest(
    val phone_number: String,
    val email:String,
    val username: String,
    val userImage: List<Image> = listOf()
)

@JsonClass(generateAdapter = true)
data class UpdatedData(
//    val _id:String,
    val username: String,
//    val password: String,
    val phone_number: String,
    val email:String,
    val is_activated: Boolean,
    val image_url:String,
    val image_id:String,
    val creation_time: String,
    val __v:Int
)

@JsonClass(generateAdapter = true)
data class UpdateUserDataResponse(
    val code: Int,
    val updatedData: UpdatedData,
    val timestamp: Long
)
