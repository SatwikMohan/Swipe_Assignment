package com.example.swipe_assignment.ApiService

import com.example.swipe_assignment.ApiService.models.AddDataResponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @Headers(
        "Content-Type:application/json"
    )
    @GET("get")
    fun getApiData():Call<JsonArray>

    @Headers(
        "Content-Type:application/json"
    )
    @Multipart
    @POST("add")
    fun addApiData(
        @Part("product_name") product_name: RequestBody,
        @Part("product_type") product_type:RequestBody,
        @Part("price") price:RequestBody,
        @Part("tax") tax:RequestBody
    ):Call<AddDataResponse>
}