package com.example.nonutsinmybasket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener{
            when {
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
                    val email: String = etRegisterEmail.text.toString().trim {it <= ' '}
                    val password: String = etRegisterPassword.text.toString().trim {it <= ' '}
                    val confirmPassword: String = etConfirmPassword.text.toString().trim {it <= ' '}

                    if(password == confirmPassword) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@Register,
                                        "You've successfully registered!",
                                        Toast.LENGTH_SHORT
                                    ).show()

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
                    } else {
                        Toast.makeText(this@Register,
                        "The two passwords entered are different",
                        Toast.LENGTH_SHORT)
                    }
                }


            }
        }
    }
}