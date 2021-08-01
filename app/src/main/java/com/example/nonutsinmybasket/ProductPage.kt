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

class ProductPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        var API = rf.create(RetrofitInterface::class.java)
        var call = API.posts

        call?.enqueue(object:Callback<ProductModel?>{
            override fun onResponse(
                call: Call<ProductModel?>?,
                response: Response<ProductModel?>?,
            ) {
                    var postResponse : ProductModel = response?.body()!! as ProductModel
                    var post = arrayOfNulls<String>(1)


                    post[0]=postResponse.product.ingredients_text

                    var adapter = ArrayAdapter<String>(applicationContext,android.R.layout.simple_dropdown_item_1line,post)
                    productData.adapter = adapter
            }

            override fun onFailure(call: Call<ProductModel?>?, t: Throwable?) {
                Toast.makeText(
                    this@ProductPage,
                    "${t.toString()}",
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