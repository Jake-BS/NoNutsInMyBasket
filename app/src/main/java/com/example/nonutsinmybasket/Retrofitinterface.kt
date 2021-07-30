package com.example.nonutsinmybasket

import retrofit2.Call
import retrofit2.http.GET

interface Retrofitinterface {

    @get:GET("posts")
    val posts : Call<List<PostModel?>?>?


    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}