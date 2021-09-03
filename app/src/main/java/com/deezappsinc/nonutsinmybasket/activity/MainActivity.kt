package com.deezappsinc.nonutsinmybasket.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import android.view.View
import com.deezappsinc.nonutsinmybasket.adapter.BottomNavViewPagerAdapter
import com.deezappsinc.nonutsinmybasket.fragments.About
import com.deezappsinc.nonutsinmybasket.fragments.Avoid
import com.deezappsinc.nonutsinmybasket.fragments.Account
import com.deezappsinc.nonutsinmybasket.fragments.Scan
import com.deezappsinc.nonutsinmybasket.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private lateinit var currentEmail: String

    lateinit var sharedPrefs: SharedPreferences

    var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.getRoot()
        setContentView(view)

        MobileAds.initialize(this@MainActivity)
        val adRequest = AdRequest.Builder().build()
        mainAdView.loadAd(adRequest)

        sharedPrefs=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPrefs.getString("Email", "1")
        userId = sharedPrefs.getString("UserId", "1")

        if (isLogin == "1") {
            val emailId = intent.getStringExtra("email_id")
            userId = intent.getStringExtra("user_id")
            if (emailId != null) {
                setText(emailId)
                with(sharedPrefs.edit()) {
                    putString("Email", emailId)
                    putString("UserId", userId)
                    apply()
                }
            } else {
                var intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }  else {
            setText(isLogin)
        }
        setFragmentsToViewPager()

    }

    private fun setText(email: String?) {
        if (email != null) {
            currentEmail = email
            //tvGreeting.text = "Welcome $currentEmail!"
        }
    }

    private fun setFragmentsToViewPager() {
        // set fragments to view pager
        val adapter = BottomNavViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(Avoid(userId), "")
        adapter.addFrag(Scan(userId), "")
        adapter.addFrag(Account(userId, sharedPrefs), "")
        adapter.addFrag(About(), "")
        binding?.viewPager?.adapter = adapter

        //page swipe and click handling
        binding?.viewPager?.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
                binding!!.bubbleNavigationLinearView.setCurrentActiveItem(i)
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })

        //page swipe and click handling
        binding?.bubbleNavigationLinearView?.setNavigationChangeListener { view, position ->
            binding?.viewPager?.setCurrentItem(
                position,
                true
            )
        }
    }
}