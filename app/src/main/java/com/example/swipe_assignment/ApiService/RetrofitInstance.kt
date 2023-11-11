package com.example.swipe_assignment.ApiService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private var retrofit: Retrofit?=null
    val baseUrl="https://app.getswipe.in/api/public/"
    fun getRetrofit():Retrofit?{
        if (retrofit==null){
            retrofit=Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl).build()
        }
        return retrofit
    }
}