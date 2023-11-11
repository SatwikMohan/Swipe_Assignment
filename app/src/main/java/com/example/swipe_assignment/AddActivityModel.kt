package com.example.swipe_assignment

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData

data class AddActivityModel(
    val productName: MutableLiveData<String>,
    val productType:MutableLiveData<String>,
    val priceValue:MutableLiveData<String>,
    val taxValue:MutableLiveData<String>
)