package com.deezappsinc.nonutsinmybasket.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.deezappsinc.nonutsinmybasket.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import io.perfmark.Link
import kotlinx.android.synthetic.main.activity_registration.*
import com.klinker.android.link_builder.*
import kotlinx.android.synthetic.main.fragment_about.*

class Registration : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var binding: ActivityRegistrationBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(
            layoutInflater
        )
        val view: View = binding!!.getRoot()
        setContentView(view)
        binding!!.toolbarBack.setOnClickListener { finish() }
        registerButtonClickListener()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        setupLink()
    }

    private fun setupLink() {
        val termsAndConditions= Link("Terms and Conditions")
            .setTextColor(Color.BLUE)
            .setTextColorOfHighlightedLink(Color.CYAN)
            .setHighlightAlpha(.4f)
            .setUnderlined(true)
            .setBold(false)
        termsAndConditions.setOnClickListener {
            var uri = Uri.parse("https://sites.google.com/view/avoidtermsandconditions/home")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        val privacyNotice= Link("Privacy Notice")
            .setTextColor(Color.BLUE)
            .setTextColorOfHighlightedLink(Color.CYAN)
            .setHighlightAlpha(.4f)
            .setUnderlined(true)
            .setBold(false)
        privacyNotice.setOnClickListener {
            var uri = Uri.parse("https://sites.google.com/view/avoid-privacy-notice/home")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        val endUserLicense= Link("End User License")
            .setTextColor(Color.BLUE)
            .setTextColorOfHighlightedLink(Color.CYAN)
            .setHighlightAlpha(.4f)
            .setUnderlined(true)
            .setBold(false)
        endUserLicense.setOnClickListener {
            var uri = Uri.parse("https://sites.google.com/view/avoid-end-user-license/home")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        val acceptableUSe = Link("Acceptable Use Policy")
        .setTextColor(Color.BLUE)
            .setTextColorOfHighlightedLink(Color.CYAN)
            .setHighlightAlpha(.4f)
            .setUnderlined(true)
            .setBold(false)
        acceptableUSe.setOnClickListener {
            var uri = Uri.parse("https://sites.google.com/view/avoid-acceptable-use-policy/home")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        policiesLink.applyLinks(termsAndConditions, privacyNotice, endUserLicense, acceptableUSe)
    }

    private fun registerButtonClickListener() {
        btnRegister.setOnClickListener{
            when {
                //Checks for empty fields
                TextUtils.isEmpty(etRegisterEmail.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@Registration,
                        "Please enter your email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etRegistrationPassword.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@Registration,
                        "Please enter your password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etRegistrationConfirmPassword.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@Registration,
                        "Please confirm your password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                !agree.isChecked -> {
                    Toast.makeText(
                        this@Registration,
                        "You must agree to the linked policies to register",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    registerClicked()
                }


            }
        }
    }
    private fun registerClicked() {
        val email: String = etRegisterEmail.text.toString().trim {it <= ' '}
        val password: String = etRegistrationPassword.text.toString().trim {it <= ' '}
        val confirmPassword: String = etRegistrationConfirmPassword.text.toString().trim {it <= ' '}

        if(password == confirmPassword) {
            val users = db.collection("USERS")
            register(email, password, users)
        } else {
            Toast.makeText(this@Registration,
                "The two passwords entered are different",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun register(email: String, password: String, users: CollectionReference) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val emptyDietArray = arrayListOf<String>()
                    val customIngredients = arrayListOf<String>()
                    val user = hashMapOf(
                        "email" to email,
                        "diets" to emptyDietArray,
                        "custom_ingredients" to customIngredients
                    )
                    val userId = firebaseUser.uid
                    users.document(userId).set(user)
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this@Registration,
                                "Failure writing doucment, $e",
                                Toast.LENGTH_SHORT
                            ).show()
                        }.addOnSuccessListener {
                            Toast.makeText(
                                this@Registration,
                                "You've successfully registered!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    val intent = Intent(this@Registration, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("user_id", userId)
                    intent.putExtra("email_id", email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@Registration,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

}