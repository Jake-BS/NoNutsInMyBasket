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