package com.example.nonutsinmybasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AvoidList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avoid_list)

        val actionBar = supportActionBar

        actionBar!!.title = "Avoid List"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}