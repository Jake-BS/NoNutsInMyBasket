package com.example.nonutsinmybasket.Fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.example.nonutsinmybasket.databinding.FragmentScanBinding
import com.example.nonutsinmybasket.productpage.ProductPage

class Scan : Fragment() {
    var binding: FragmentScanBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentScanBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        binding!!.scannedresult.setOnClickListener {
            val intent = Intent(context, ProductPage::class.java)
            startActivity(intent)
        }
        return view
    }
}