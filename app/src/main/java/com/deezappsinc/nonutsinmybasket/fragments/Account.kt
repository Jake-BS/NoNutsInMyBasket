package com.deezappsinc.nonutsinmybasket.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.deezappsinc.nonutsinmybasket.activity.Login
import com.deezappsinc.nonutsinmybasket.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_account.*

class Account(var userId: String?, var sharedPrefs: SharedPreferences) : Fragment() {
    var binding: FragmentAccountBinding? = null
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding =
            FragmentAccountBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLogoutButton(sharedPrefs)
        db = FirebaseFirestore.getInstance()
        setupUpdateButton()
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

    private fun setupUpdateButton() {
        btnUpdate.setOnClickListener{
            when {
                TextUtils.isEmpty(etUpdatePassword.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        activity,
                        "Please enter your password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etConfirmUpdatePassword.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        activity,
                        "Please confirm your password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    updateClicked()
                }
            }
        }
    }

    private fun updateClicked() {
        val password: String = etUpdatePassword.text.toString().trim {it <= ' '}
        val confirmPassword: String = etConfirmUpdatePassword.text.toString().trim {it <= ' '}

        if(password == confirmPassword) {
            updateDetails(password)
        } else {
            Toast.makeText(activity,
                "The two passwords entered are different",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateDetails(password: String) {
        FirebaseAuth.getInstance().currentUser?.updatePassword(password)?.addOnFailureListener { e ->
            Toast.makeText(
                activity,
                "Failed to update password, $e",
                Toast.LENGTH_SHORT
            ).show()
        }?.addOnSuccessListener {
            Toast.makeText(
                activity,
                "Password updated!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}