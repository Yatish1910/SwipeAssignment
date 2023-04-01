package com.example.swipeassignment.fragments.listProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipeassignment.model.Model
import com.example.swipeassignment.model.Repository
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val repository: Repository
) : ViewModel() {

    private val productLiveData: MutableLiveData<List<Model>> = MutableLiveData<List<Model>>()
    val product: LiveData<List<Model>>
        get() = productLiveData

    init {
//        viewModelScope.launch {
//            repository.getImages()
//        }
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val data = repository.getImages()
            if(data.isNotEmpty()){
                productLiveData.postValue(data)
            }
        }
    }


}