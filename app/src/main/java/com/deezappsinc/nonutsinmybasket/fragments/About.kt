package com.deezappsinc.nonutsinmybasket.fragments
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.deezappsinc.nonutsinmybasket.databinding.FragmentAboutBinding

//import androidx.appcompat.widget.SearchView;
class About() : Fragment() {
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