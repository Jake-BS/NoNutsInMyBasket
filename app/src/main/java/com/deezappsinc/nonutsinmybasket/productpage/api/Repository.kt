package com.deezappsinc.nonutsinmybasket.productpage.api

import com.deezappsinc.nonutsinmybasket.models.apiclasses.ProductModel
import retrofit2.Response

class Repository {

    suspend fun getPostDynamic(barcode: String): Response<ProductModel> {
        return RetrofitInstance.api.getPostDynamic(barcode)
    }
}