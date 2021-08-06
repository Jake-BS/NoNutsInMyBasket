package com.example.nonutsinmybasket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButtonClickListener()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    fun registerButtonClickListener() {
        btnRegister.setOnClickListener{
            when {
                //Checks for empty fields
                TextUtils.isEmpty(etRegisterEmail.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@Register,
                        "Please enter your email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etRegisterPassword.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@Register,
                        "Please enter your password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etConfirmPassword.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@Register,
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
        val password: String = etRegisterPassword.text.toString().trim {it <= ' '}
        val confirmPassword: String = etConfirmPassword.text.toString().trim {it <= ' '}

        if(password == confirmPassword) {


            val users = db.collection("USERS")
            users.whereEqualTo("email", email).get()
                .addOnSuccessListener {
                    it ->
                    if(it.isEmpty) {
                        register(email, password, users)
                    } else {
                        Toast.makeText(this,
                        "Email already registered, please try logging in",
                        Toast.LENGTH_LONG).show()
                    }
                }


        } else {
            Toast.makeText(this@Register,
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
                    users.document(email).set(user)
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this@Register,
                                "Failure writing doucment, $e",
                                Toast.LENGTH_SHORT
                            ).show()
                        }.addOnSuccessListener {
                            Toast.makeText(
                                this@Register,
                                "You've successfully registered!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    val intent = Intent(this@Register, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("user_id", firebaseUser.uid)
                    intent.putExtra("email_id", email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@Register,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}