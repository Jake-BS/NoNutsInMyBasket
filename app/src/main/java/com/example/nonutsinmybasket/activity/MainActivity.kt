package com.example.nonutsinmybasket.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import android.view.View
import com.example.nonutsinmybasket.adapter.BottomNavViewPagerAdapter
import com.example.nonutsinmybasket.fragments.About
import com.example.nonutsinmybasket.fragments.Avoid
import com.example.nonutsinmybasket.fragments.Profile
import com.example.nonutsinmybasket.fragments.Scan
import com.example.nonutsinmybasket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private lateinit var currentEmail: String

    lateinit var sharedPrefs: SharedPreferences

    var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.getRoot()
        setContentView(view)

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
        adapter.addFrag(Profile(userId, sharedPrefs), "")
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