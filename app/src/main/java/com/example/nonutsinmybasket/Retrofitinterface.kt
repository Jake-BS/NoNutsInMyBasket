package com.example.nonutsinmybasket

import com.example.nonutsinmybasket.apiclasses.ProductModel
import retrofit2.Call
import retrofit2.http.GET

interface Retrofitinterface {

    @get:GET("8003340090535.json")
    val posts : Call<List<ProductModel?>?>?


    companion object {
        const val BASE_URL = "https://world.openfoodfacts.org/api/v0/product/"
    }
}