package com.example.nonutsinmybasket

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

private const val CAMERA_REQUEST_CODE = 101

class MainActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    private lateinit var avoidButton: Button

    private lateinit var scanButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        tvGreeting.text = "Welcome $userId, $emailId"

        btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LoginPage::class.java))
            finish()
        }

        setupPermissions()
        codeScanner()

        scanButton = findViewById(R.id.scanButton)
        scanButton.setOnClickListener{
            if (scanner_view.getVisibility() == View.INVISIBLE) {
                scanner_view.visibility = View.VISIBLE
                scanPrompt.visibility = View.VISIBLE
            }else{
                scanner_view.visibility = View.INVISIBLE
                scanPrompt.visibility = View.INVISIBLE

            }
        }


        avoidButton = findViewById(R.id.avoidButton)
        avoidButton.setOnClickListener {
            val intent = Intent(this, AvoidList::class.java)
            startActivity(intent)
        }
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, scanner_view)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    scanPrompt.text = it.text

                }


            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Problem scanning barcode:  ${it.message}")
                }
            }
        }
        scanner_view.setOnClickListener{
            val intent = Intent(this, product_page::class.java)
            intent.putExtra("Barcode", scanPrompt.text.toString())
            startActivity(intent)
            codeScanner.startPreview()
        }

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray, )
    {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show()
                } else {
                    //works
                }
            }

        }

    }
}