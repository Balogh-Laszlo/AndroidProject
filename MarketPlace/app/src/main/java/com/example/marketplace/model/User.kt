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
