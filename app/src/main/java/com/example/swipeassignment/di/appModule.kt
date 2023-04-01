package com.example.swipeassignment

import com.example.swipeassignment.data.ApiCalls
import com.example.swipeassignment.model.Repository
import com.example.swipeassignment.fragments.addProduct.AddProductViewModel
import com.example.swipeassignment.fragments.listProduct.ProductListViewModel
import com.example.swipeassignment.utils.Constants
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCalls::class.java)
    }
    single {
        Repository(get())
    }
    viewModel {
      ProductListViewModel(get())
    }
    viewModel{
        AddProductViewModel(get())
    }
}