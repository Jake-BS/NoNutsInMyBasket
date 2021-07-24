package com.example.nonutsinmybasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class product_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        val intent = getIntent()
        val barcodeData= intent.getStringExtra("Barcode")

        val actionBar = supportActionBar

        actionBar!!.title = barcodeData

        actionBar.setDisplayHomeAsUpEnabled(true)

    }
}