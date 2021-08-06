package com.example.nonutsinmybasket.productpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nonutsinmybasket.R
import com.example.nonutsinmybasket.productpage.api.MainViewModel
import com.example.nonutsinmybasket.productpage.api.MainViewModelFactory
import com.example.nonutsinmybasket.productpage.api.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_page.*

class ProductPage : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)
        val intent = getIntent()

        val barcodeData = intent.getStringExtra("Barcode")
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        if (barcodeData != null) {
            viewModel.getPostDynamic(barcodeData)
            viewModel.myResponseDynamic.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    response.body()?.product?.let { Log.d("Response", it.ingredients_text) }
                    productIngredients.text = response.body()?.product?.ingredients_text
                    response.body()?.product?.let { it.image_front_url?.let { it1 ->
                        Log.d("Response",
                            it1)
                    } }
                    val imageURL = response.body()?.product?.image_front_url
                    val placeholderImage = "https://www.ecpgr.cgiar.org/fileadmin/templates/ecpgr.org/Assets/images/No_Image_Available.jpg"
                    if(imageURL==null)
                        Picasso.get().load(placeholderImage).into(imageView)
                    else {
                        val into = Picasso.get().load(imageURL).into(imageView)
                    }



                } else {
                    response.errorBody()?.let { Log.d("Response", it.string()) }
                    productIngredients.text = response.code().toString()
                }
            })
        }


        val actionBar = supportActionBar
        actionBar!!.title = "Ingredient Data"
        actionBar.setDisplayHomeAsUpEnabled(true)


    }

}