package com.example.nonutsinmybasket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nonutsinmybasket.databinding.ActivityScannedResultBinding

class ScanedResult : AppCompatActivity() {
    private var binding: ActivityScannedResultBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannedResultBinding.inflate(
            layoutInflater
        )
        val view: View = binding!!.getRoot()
        setContentView(view)
        binding!!.toolbarBack.setOnClickListener { finish() }
    }
}