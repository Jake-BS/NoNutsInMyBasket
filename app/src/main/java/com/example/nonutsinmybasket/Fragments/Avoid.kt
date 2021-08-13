package com.example.nonutsinmybasket.Fragments

import androidx.annotation.RequiresApi
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.example.nonutsinmybasket.R

class Avoid : Fragment() {
    private val btnToggleDark: Switch? = null

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_avoid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}
}