package com.example.nonutsinmybasket.Fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.nonutsinmybasket.R
import com.example.nonutsinmybasket.databinding.FragmentAboutBinding
import com.example.nonutsinmybasket.databinding.FragmentProfileBinding

//import androidx.appcompat.widget.SearchView;
class About : Fragment() {
    var binding: FragmentAboutBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentAboutBinding.inflate(layoutInflater)
        return binding!!.root
    }
}