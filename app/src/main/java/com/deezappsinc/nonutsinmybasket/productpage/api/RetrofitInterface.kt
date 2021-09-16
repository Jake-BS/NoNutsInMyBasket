package com.deezappsinc.nonutsinmybasket.productpage.api

import com.deezappsinc.nonutsinmybasket.models.apiclasses.ProductModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitInterface {
    companion object {
        const val BASE_URL = "https://world.openfoodfacts.org/api/v0/"
    }

    @GET("product/{rBarcode}.json")
    suspend fun getPostDynamic(
        @Path("rBarcode") barcode: String
    ): Response<ProductModel>
}