# Swipe_Assignment
Submitted by - Satwik Mohan

# Building and running the application
Go to the master branch and clone the repository using the Git Url and import the project int the Android Studio. The project will begin to build.
You can use the application by running it through the Android Studio on an emulator or your android mobile phones.
You can use the apk in the given drive link to install the application directly into your android mobile phones.

# Dependency used
Retrofit
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

OkHTTPClient 
implementation 'com.squareup.okhttp3:okhttp:4.9.1'
implementation "com.squareup.okhttp3:logging-interceptor:4.7.2"

LifeCycle 
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2'
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.2'
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.6.2'
implementation 'androidx.activity:activity-ktx:1.8.0'

Coroutines 
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

# Screen 1 -> MainActivity.kt
The MainActivity consist of the fragment GetDataFragment. In the GetDataFragment the getApiData() function is called using the GetDataFragmentViewModel to get the list of all the products. Each element of the product list is an object of the TileModel data class.
The getApiData() function calls the function getApiData() in the ApiInterface class present in the ApiService package to call a GET Request using the given API - https://app.getswipe.in/api/public/get
There are two other method calls the GetDataFragment, One to go to AddDataActivity to add product data and other to Reload or Refresh the Data.

# Screen 2 -> AddDataActivity.kt
The AddDataActivity consists of a series of EditTexts to take a compulsory filled information of ProductName, ProductType, Price and Tax. There is an optional option to attach the image of the product. The SubmitButton will call the function sendDataToServer() in the AddDataActivityViewMdodel to call a POST Request using the given API - https://app.getswipe.in/api/public/add and data to the database.
