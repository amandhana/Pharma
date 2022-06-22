package com.pharma.activity.login.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.agot.support.Utils
import com.pharma.R
import com.pharma.activity.otp.OtpActivity
import com.pharma.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding!!.cvVerifyPhoneNumber.setOnClickListener(this)
    }

    private fun goToOtpActivity() {
        Utils.startActivityFinish(mActivity!!, OtpActivity::class.java)
    }


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.cv_verify_phone_number) {
            goToOtpActivity()
        }/*else if (id == R.id.cv_skip) {
            Utils.startActivityFinish(mActivity!!, HomeActivity::class.java)
        }*/
    }
}