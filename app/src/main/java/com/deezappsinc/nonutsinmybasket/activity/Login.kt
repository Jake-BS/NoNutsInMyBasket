package com.deezappsinc.nonutsinmybasket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.deezappsinc.nonutsinmybasket.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view: View = binding!!.getRoot()
        setContentView(view)

        binding!!.submit.setOnClickListener {when {
            TextUtils.isEmpty(etLoginEmail.text.toString().trim {it <= ' '}) -> {
                Toast.makeText(
                    this@Login,
                    "Please enter your email",
                    Toast.LENGTH_SHORT
                ).show()
            }
            TextUtils.isEmpty(etLoginPassword.text.toString().trim {it <= ' '}) -> {
                Toast.makeText(
                    this@Login,
                    "Please enter your password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                login()
            }
        }
        }

        binding!!.btnSignUp.setOnClickListener {
            val intent = Intent(baseContext, Registration::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val email: String = etLoginEmail.text.toString().trim {it <= ' '}
        val password: String = etLoginPassword.text.toString().trim {it <= ' '}
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Toast.makeText(
                        this@Login,
                        "You've successfully logged in!",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(baseContext, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                    intent.putExtra("email_id", email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@Login,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}