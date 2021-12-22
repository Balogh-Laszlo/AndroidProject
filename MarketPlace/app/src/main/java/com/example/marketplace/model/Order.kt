package com.example.marketplace.model

import com.squareup.moshi.JsonClass

data class OrderRequest(var title:String="", var description:String="", var perice_per_unit:String="", var units:String="", var owner_username:String="")

@JsonClass(generateAdapter = true)
data class OrderResponse(val creation:String,
                         val order_id:String,
                         val username:String,
                         val status:String,
                         val owner_username:String,
                         val price_per_unit:String,
                         val units: String,
                         val description: String,
                         val title: String,
                         val image: List<Image> = listOf(),
                         val creation_time:Long
                         )




