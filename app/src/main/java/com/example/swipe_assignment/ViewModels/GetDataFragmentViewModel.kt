package com.example.swipe_assignment.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipe_assignment.ApiService.ApiInterface
import com.example.swipe_assignment.ApiService.RetrofitInstance
import com.example.swipe_assignment.RecyclerViewAdapter
import com.example.swipe_assignment.TileModel
import com.google.gson.JsonArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetDataFragmentViewModel:ViewModel() {

    private val _list:MutableLiveData<ArrayList<TileModel>> = MutableLiveData()
    val arrayList:LiveData<ArrayList<TileModel>> get() = _list
    fun getApiData(context: Context){
        val apiInterface= RetrofitInstance.getRetrofit()?.create(ApiInterface::class.java)
        val list=ArrayList<TileModel>()
        apiInterface?.getApiData()?.enqueue(object :Callback<JsonArray>{
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonArray=response.body()
                for(element in jsonArray!!){
                    val jsonObject=element.asJsonObject
                    val image=jsonObject.get("image").toString()
                    val product_name=jsonObject.get("product_name").toString().replace("\"","")
                    val product_type=jsonObject.get("product_type").toString().replace("\"","")
                    val price=jsonObject.get("price").toString()
                    val tax=jsonObject.get("tax").toString()
                    list.add(TileModel(image,price,product_name,product_type,tax))
                }
                _list.value=list
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Toast.makeText(context,"Failure", Toast.LENGTH_LONG).show()
            }

        })
    }
}