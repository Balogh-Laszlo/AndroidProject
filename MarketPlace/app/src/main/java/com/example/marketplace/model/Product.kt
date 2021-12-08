package com.example.marketplace.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(val _id: String, val image_id: String, val image_name: String, val image_path: String)

@JsonClass(generateAdapter = true)
data class Product(var rating: Double =0.0,
                   var amount_type: String = "",
                   var price_type: String ="",
                   var product_id: String = "",
                   var username: String = "",
                   var is_active: Boolean = true,
                   var price_per_unit: Double = 0.0,
                   var units: Int = 0,
                   var description: String = "",
                   var title: String ="",
                   var images: List<Image> = listOf(),
                   var creation_time: Long = 0
)



@JsonClass(generateAdapter = true)
data class ProductResponse(val item_count: Int, val products: List<Product>, val timestamp: Long)

@JsonClass(generateAdapter = true)
data class AddProductRequest(
    val uploadImages: List<Image>,
    val title: String,
    val description:String,
    val price_per_unit:Double,
    val units: Int,
    val is_active:Boolean,
    val rating:Double,
    val amount_type: String,
    val price_type: String
)
