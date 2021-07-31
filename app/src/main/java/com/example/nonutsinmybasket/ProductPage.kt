package com.example.nonutsinmybasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.nonutsinmybasket.apiclasses.ProductModel
import kotlinx.android.synthetic.main.activity_product_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class ProductPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        var rf = Retrofit.Builder()
            .baseUrl(Retrofitinterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        var API = rf.create(Retrofitinterface::class.java)
        var call = API.posts

        call?.enqueue(object:Callback<List<ProductModel?>?>{
            override fun onResponse(
                call: Call<List<ProductModel?>?>?,
                response: Response<List<ProductModel?>?>?,
            ) {
                    var postlist : List<ProductModel>? = response?.body() as List<ProductModel>
                    var post = arrayOfNulls<String>(postlist!!.size)

                    for (i in postlist!!.indices)
                        post[i]=postlist!![i]!!.product.ingredients_text

                    var adapter = ArrayAdapter<String>(applicationContext,android.R.layout.simple_dropdown_item_1line,post)
                    productData.adapter = adapter
            }

            override fun onFailure(call: Call<List<ProductModel?>?>?, t: Throwable?) {
                Toast.makeText(
                    this@ProductPage,
                    "AHHHHHHHHHHHHHHHHHHHHHH!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

        val intent = getIntent()
        val barcodeData= intent.getStringExtra("Barcode")

        val actionBar = supportActionBar

        actionBar!!.title = barcodeData

        actionBar.setDisplayHomeAsUpEnabled(true)
    }

}