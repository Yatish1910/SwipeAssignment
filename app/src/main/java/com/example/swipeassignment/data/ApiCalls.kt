package com.example.swipeassignment.data

import com.example.swipeassignment.model.Model
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiCalls {
    @GET("/api/public/get")
    suspend fun getImages(): List<Model>

    @POST("/api/public/add")
    suspend fun addProduct(
        @Body data: RequestBody
    ): retrofit2.Response<Any>

}