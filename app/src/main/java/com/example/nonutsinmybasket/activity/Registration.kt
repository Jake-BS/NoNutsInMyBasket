package com.example.nonutsinmybasket.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.nonutsinmybasket.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registration.*

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