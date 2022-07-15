package com.shoponn.activity.otp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.shoponn.support.Utils
import com.shoponn.R
import com.shoponn.activity.home.activity.HomeActivity
import com.shoponn.databinding.ActivityOtpBinding

class OtpActivity : AppCompatActivity(), View.OnClickListener  {
    var mActivity: Activity? = null
    var binding: ActivityOtpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
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
    }

    private fun setUpClickListener() {
           binding!!.tvConfirm.setOnClickListener(this)
    }

    private fun goToDashboardActivity() {
        Utils.startActivityFinish(mActivity!!, HomeActivity::class.java)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
          if (id == R.id.tv_confirm)
              goToDashboardActivity()
    }

}