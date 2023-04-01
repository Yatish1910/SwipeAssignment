package com.example.swipeassignment.fragments.addProduct

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipeassignment.model.Model
import com.example.swipeassignment.model.ProductModel
import com.example.swipeassignment.model.Repository
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

class AddProductViewModel(
    private val repository: Repository
):ViewModel() {
    val addProductLiveData: MutableLiveData<Boolean> = MutableLiveData()

    // This is where network call is happens
    private fun addProduct(productModel: ProductModel){
        val productBody = createProductBody(productModel)
        viewModelScope.launch {
            val response = repository.addProducts(productBody)
            try {
                if(response.isSuccessful){
                    Log.d("upload Success",response.body().toString())
                    addProductLiveData.postValue(true)
                }
            }catch (ex:Exception){
                addProductLiveData.postValue(false)
            }
        }
    }

    /*tThis function is for validating user's input.*/
    fun checkInput(productName:String,productType:String,productPrice:String,productTax:String,productImage: File?):Boolean{
        if(productName.trim().isEmpty()) return false
        if(productType.trim().isEmpty()) return false
        if(productPrice.trim().isEmpty()) return false
        if(productTax.trim().isEmpty()) return false
        val model = Model(" ",productPrice.toDouble(),productName,productType,productTax.toDouble())
        addProduct(productModel = ProductModel(model,productImage))
        return true
    }

    // This the function where we are putting our all values in multipart.
    private fun createProductBody(productModel: ProductModel):MultipartBody{
        val rawData = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("product_name", productModel.model.mProductName)
            .addFormDataPart("product_type", productModel.model.mProductType)
            .addFormDataPart("price", productModel.model.mPrice.toString())
            .addFormDataPart("tax", productModel.model.mTax.toString())
        if(productModel.file!=null){
            rawData.addFormDataPart(
                "files[]",
                productModel.file.name,
                RequestBody.create(MediaType.parse("multipart/form-data"), productModel.file)
            )
        }
        return rawData.build()
    }
    //Changing uri to file

    fun uriToFile(uri: Uri,context: Context): File {
        val fileDir = context.filesDir
        val file = File(fileDir, "image.png")
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)
        return file
    }
}