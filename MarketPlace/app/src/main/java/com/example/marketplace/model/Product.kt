package com.example.marketplace.model

import com.squareup.moshi.JsonClass

data class AddProductData(var title: String ="", var price: String ="", var price_type: String ="", var amount: String="", var amount_type: String="", var description: String="", var sellerName: String="", var sellerEmail: String="", var phoneNumber: String="")

enum class Screen{TimeLine, MyMarket}

@JsonClass(generateAdapter = true)
data class Image(val _id: String ="", val image_id: String ="", val image_name: String= "", val image_path: String="")

@JsonClass(generateAdapter = true)
data class Product(var rating: Double =0.0,
                   var amount_type: String = "",
                   var price_type: String ="",
                   var product_id: String = "",
                   var username: String = "",
                   var is_active: Boolean = true,
                   var price_per_unit: String = "0.0",
                   var units: String = "0",
                   var description: String = "",
                   var title: String ="",
                   var images: List<Image> = listOf(),
                   var creation_time: Long = 0
)

data class DeleteRequest(
    val product_id: String,
    val token:String
)

@JsonClass(generateAdapter = true)
data class DeleteResponse(
    val message:String,
    val product_id: String,
    val deletion_time:String
)

@JsonClass(generateAdapter = true)
data class ProductResponse(val item_count: Int, val products: List<Product>, val timestamp: Long)

@JsonClass(generateAdapter = true)
data class AddProductRequest(
    val uploadImages: List<Image>,
    val title: String,
    val description:String,
    val price_per_unit:String,
    val units: String,
    val is_active:Boolean,
    val rating:Double,
    val amount_type: String,
    val price_type: String
)
