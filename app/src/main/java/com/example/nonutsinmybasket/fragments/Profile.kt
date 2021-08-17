package com.example.nonutsinmybasket.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nonutsinmybasket.R
import com.example.nonutsinmybasket.activity.Login
import com.example.nonutsinmybasket.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.view.*

class Profile(var userId: String?, var sharedPrefs: SharedPreferences) : Fragment() {
    var binding: FragmentProfileBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding =
            FragmentProfileBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLogoutButton(sharedPrefs)
    }

    private fun setupLogoutButton(sharedPrefs: SharedPreferences) {
        binding?.profileFooter?.setOnClickListener {
            sharedPrefs.edit().remove("Email").apply()
            sharedPrefs.edit().remove("UserId").apply()
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context, Login::class.java))
            activity?.finish()
        }
    }
}