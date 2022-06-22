package com.pharma.activity.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.pharma.activity.home.activity.HomeActivity
import com.pharma.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    var mActivity: Activity? = null
    var binding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        Handler().postDelayed({
            val mainIntent = Intent(this@SplashActivity, HomeActivity::class.java)
            this@SplashActivity.startActivity(mainIntent)
            finish()
        }, 3000)
    }
}