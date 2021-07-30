package com.example.nonutsinmybasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_product_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class product_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        var rf = Retrofit.Builder()
            .baseUrl(Retrofitinterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        var API = rf.create(Retrofitinterface::class.java)
        var call = API.posts

        call?.enqueue(object:Callback<List<PostModel?>?>{
            override fun onResponse(
                call: Call<List<PostModel?>?>?,
                response: Response<List<PostModel?>?>?,
            ) {
                    var postlist : List<PostModel>? = response?.body() as List<PostModel>
                    var post = arrayOfNulls<String>(postlist!!.size)

                    for (i in postlist!!.indices)
                        post[i]=postlist!![i]!!.body

                    var adapter = ArrayAdapter<String>(applicationContext,android.R.layout.simple_dropdown_item_1line,post)
                    productData.adapter = adapter
            }

            override fun onFailure(call: Call<List<PostModel?>?>?, t: Throwable?) {
            }

        })

        val intent = getIntent()
        val barcodeData= intent.getStringExtra("Barcode")

        val actionBar = supportActionBar

        actionBar!!.title = barcodeData

        actionBar.setDisplayHomeAsUpEnabled(true)
    }

}