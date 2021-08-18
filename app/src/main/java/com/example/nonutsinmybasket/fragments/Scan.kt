package com.example.nonutsinmybasket.fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.nonutsinmybasket.databinding.FragmentScanBinding
import com.example.nonutsinmybasket.productpage.ProductPage
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import kotlinx.android.synthetic.main.fragment_scan.*
import kotlinx.android.synthetic.main.fragment_scan.view.*

class Scan(var userId: String?) : Fragment() {
    var binding: FragmentScanBinding? = null
    private lateinit var codeScanner: CodeScanner

    companion object {
        private const val CAMERA_REQUEST_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentScanBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setupPermissions()
        codeScanner(view)
        return view
    }

    private fun codeScanner(view: View) {

        codeScanner = activity?.let { CodeScanner(it, view.scanner_view) }!!

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.CONTINUOUS
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false




            decodeCallback = DecodeCallback {
                activity?.runOnUiThread {

                    view.scanPrompt.text = it.text
                    view.scanPrompt.visibility = View.INVISIBLE
                    val intent = Intent(context, ProductPage::class.java)
                    intent.putExtra("Barcode", view.scanPrompt.text.toString())
                    if (userId != null) intent.putExtra("user_id", userId)
                    startActivity(intent)
                }
            }


            errorCallback = ErrorCallback {
                activity?.runOnUiThread {
                    Log.e("Main", "Problem scanning barcode:  ${it.message}")
                }
            }
        }
        codeScanner.startPreview()
    }

    private fun setupPermissions() {
        val permission = activity?.let {
            ContextCompat.checkSelfPermission(
                it,
                android.Manifest.permission.CAMERA)
        }

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it, arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE)
        }
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
                    Toast.makeText(activity, "Camera permission required", Toast.LENGTH_SHORT).show()
                } else {
                    //works
                }
            }

        }

    }
}