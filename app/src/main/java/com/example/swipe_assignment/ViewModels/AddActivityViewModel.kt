package com.example.swipe_assignment.ViewModels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipe_assignment.R
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class AddActivityViewModel:ViewModel() {
    private val client = OkHttpClient()
//    private val _file:MutableLiveData<File?> = MutableLiveData()
//    val file:LiveData<File?> get() = _file
//    init {
//        _file.value=null
//    }
    private val _response:MutableLiveData<okhttp3.Response> = MutableLiveData()
    val res:LiveData<okhttp3.Response> get() = _response
    fun onClickAddImageButton(){

    }
    fun onClickSubmitButton(product_name:String,
                            product_type:String,
                            price:String,
                            tax:String,
                            file:File?
    ){
        sendDataToServer(product_name,product_type,price,tax,file)
    }

    fun sendDataToServer(data1: String, data2: String,data3:String,data4:String,file:File?) {
        val requestBody: RequestBody =
            if(file==null)
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("product_name", data1)
                    .addFormDataPart("product_type", data2)
                    .addFormDataPart("price", data3)
                    .addFormDataPart("tax", data4)
                    .build()
            else{
                val f=file
                val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), f)
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("product_name", data1)
                    .addFormDataPart("product_type", data2)
                    .addFormDataPart("price", data3)
                    .addFormDataPart("tax", data4)
                    .addFormDataPart("files[]", f.name, requestBody)
                    .build()
            }



        val request = Request.Builder()
            .url("https://app.getswipe.in/api/public/add")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                _response.postValue(response)
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    Log.d("AddResponse1",responseData.toString())
                    // Process the response data here
                } else {
                    Log.d("AddResponse1","Failure")
                    // Handle unsuccessful response
                    //Toast.makeText(context,"Data Addition UnSuccessful", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Handle failure
                Log.d("AddResponse1",e.toString())
            }
        })
    }

}