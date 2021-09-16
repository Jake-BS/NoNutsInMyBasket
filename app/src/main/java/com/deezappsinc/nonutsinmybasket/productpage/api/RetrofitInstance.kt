package com.deezappsinc.nonutsinmybasket.productpage.api

import com.deezappsinc.nonutsinmybasket.productpage.api.RetrofitInterface.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    val api: RetrofitInterface by lazy {
        retrofit.create(RetrofitInterface::class.java)
    }
}