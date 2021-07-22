package com.example.nonutsinmybasket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var avoidButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        avoidButton = findViewById(R.id.avoidButton)
        avoidButton.setOnClickListener {
            val intent = Intent(this, AvoidList::class.java)
            startActivity(intent)
        }
    }
}