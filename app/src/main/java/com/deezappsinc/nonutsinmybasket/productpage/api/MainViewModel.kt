package com.deezappsinc.nonutsinmybasket.productpage.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deezappsinc.nonutsinmybasket.models.apiclasses.ProductModel
import retrofit2.Response
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    val myResponseDynamic: MutableLiveData<Response<ProductModel>> = MutableLiveData()

    fun getPostDynamic(barcode: String) {
        viewModelScope.launch {
            val response = repository.getPostDynamic(barcode)
            myResponseDynamic.value = response
        }
    }
}