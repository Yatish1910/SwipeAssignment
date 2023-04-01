package com.example.swipeassignment.model

import com.google.gson.annotations.SerializedName

data class Model(
        @SerializedName("image") val mImage:String,
        @SerializedName("price") val mPrice:Double,
        @SerializedName("product_name") val mProductName:String,
        @SerializedName("product_type") val mProductType:String,
        @SerializedName("tax") val mTax:Double
):java.io.Serializable
