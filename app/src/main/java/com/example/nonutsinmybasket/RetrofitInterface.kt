package com.example.nonutsinmybasket

import com.example.nonutsinmybasket.apiclasses.ProductModel
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {

    @get:GET("product/8003340090535.json")
    val posts : Call<ProductModel?>?


    companion object {
        const val BASE_URL = "https://world.openfoodfacts.org/api/v0/"
    }
}