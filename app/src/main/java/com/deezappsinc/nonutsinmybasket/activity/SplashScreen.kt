package com.deezappsinc.nonutsinmybasket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Looper
import android.os.Handler
import android.view.View
import com.deezappsinc.nonutsinmybasket.BuildConfig
import com.deezappsinc.nonutsinmybasket.databinding.ActivitySplashBinding

class SplashScreen : AppCompatActivity() {
    private var binding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view: View = binding!!.getRoot()
        setContentView(view)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
        val versionName = BuildConfig.VERSION_NAME
        binding!!.version.text = "Version - $versionName"
    }
}