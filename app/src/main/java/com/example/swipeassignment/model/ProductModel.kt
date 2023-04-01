package com.example.swipeassignment.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.io.File

data class ProductModel(
    val model: Model,
    val file : File?
)
