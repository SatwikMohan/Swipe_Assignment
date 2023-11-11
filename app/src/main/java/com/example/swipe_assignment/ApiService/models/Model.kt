package com.example.swipe_assignment.ApiService.models

import com.google.gson.annotations.SerializedName

data class Model(
    @SerializedName("image")
    val image:String,
    @SerializedName("price")
    val price:Float,
    @SerializedName("product_name")
    val product_name:String,
    @SerializedName("product_type")
    val product_type:String,
    @SerializedName("tax")
    val tax:Float
)
