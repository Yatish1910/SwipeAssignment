package com.example.swipeassignment.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.swipeassignment.data.ApiCalls
import okhttp3.RequestBody
import retrofit2.http.Body

class Repository(
    private val userApi:ApiCalls
) {

    suspend fun getImages():List<Model> = userApi.getImages()

    suspend fun addProducts(body: RequestBody) = userApi.addProduct(body)
}