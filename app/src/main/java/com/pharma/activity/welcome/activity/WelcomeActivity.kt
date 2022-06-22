package com.pharma.activity.welcome.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.pharma.activity.welcome.adapter.WelcomeSliderAdapter
import com.agot.support.Utils
import com.pharma.R
import com.pharma.activity.login.activity.LoginActivity
import com.pharma.databinding.ActivityWelcomeBinding
import java.util.*

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityWelcomeBinding? = null
    var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        init()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Utils.hideKeyboard(mActivity!!)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpSlider()
        startAutoScrollViewPager()
    }

    private fun setUpClickListener() {
    }

    private fun setUpSlider() {
        binding!!.viewPager.adapter = WelcomeSliderAdapter(mActivity!!)
        binding!!.pageIndicator.attachTo(binding!!.viewPager)
    }

    private fun goToLoginActivity() {
        Utils.startActivityFinish(mActivity!!, LoginActivity::class.java)
    }

    private fun startAutoScrollViewPager() {
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                binding!!.viewPager.post({ binding!!.viewPager.setCurrentItem((binding!!.viewPager.getCurrentItem() + 1) % 3) })
            }
        }
        timer = Timer()
        timer!!.schedule(timerTask, 3000, 3000)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
    }

    override fun onDestroy() {
        timer!!.cancel()
        super.onDestroy()
    }
}