package com.example.swipe_assignment.ApiService.models

import com.google.gson.annotations.SerializedName

data class AddDataResponse(
    @SerializedName("message")
    val message:String,
    @SerializedName("product_details")
    val product_details:Model,
    @SerializedName("product_id")
    val product_id:Int,
    @SerializedName("success")
    val success:Boolean
)
