package com.example.nonutsinmybasket.productpage.api

import com.example.nonutsinmybasket.models.apiclasses.ProductModel
import retrofit2.Response

class Repository {

    suspend fun getPostDynamic(barcode: String): Response<ProductModel> {
        return RetrofitInstance.api.getPostDynamic(barcode)
    }
}