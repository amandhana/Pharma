package com.shoponn.activity.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.shoponn.support.Preference
import com.shoponn.activity.home.activity.HomeActivity
import com.shoponn.activity.login.activity.LoginActivity
import com.shoponn.databinding.ActivitySplashBinding


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    var mActivity: Activity? = null
    var binding: ActivitySplashBinding? = null
    var preference: Preference? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Disable Night Mode
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(this)
        Handler().postDelayed({
            var mainIntent: Intent? = if (preference?.getString("CustomerRegId")?.isEmpty() == true) {
                Intent(this@SplashActivity, LoginActivity::class.java)
            } else {
                Intent(this@SplashActivity, HomeActivity::class.java)
            }
            this@SplashActivity.startActivity(mainIntent)
            finishAffinity()
        }, 1000)
    }
}