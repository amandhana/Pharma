package com.pharma.activity.login.activity

import android.app.Activity
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.agot.api.Communicator
import com.agot.api.Constants
import com.agot.support.Utils
import com.loopj.android.http.RequestParams
import com.pharma.R
import com.pharma.activity.otp.OtpActivity
import com.pharma.activity.signup.activity.SignUpActivity
import com.pharma.api.CustomResponseListener
import com.pharma.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "LoginActivity"

    var mActivity: Activity? = null
    var binding: ActivityLoginBinding? = null

    private lateinit var progressDialog: ProgressDialog

    private lateinit var stPassword: String
    private lateinit var stUsername: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        progressDialog = ProgressDialog(mActivity)
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding!!.cvLogin.setOnClickListener(this)
        binding!!.tvSignUp.setOnClickListener(this)
    }

    private fun goToOtpActivity() {
        Utils.startActivityFinish(mActivity!!, OtpActivity::class.java)
    }


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.cv_login) {
            login()
        }else if (id == R.id.tv_sign_up) {
            Utils.startActivity(mActivity!!, SignUpActivity::class.java)
        }
    }

    private fun login() {
        if (binding!!.etUsername.text.toString().isEmpty()) {
            binding!!.etUsername.error = "Username can't be empty!"
            binding!!.etUsername.requestFocus()
            Utils.showKeyboard(this)
        } else if (binding!!.etPassword.text.toString().isEmpty()) {
            binding!!.etPassword.error = "Password can't be empty!"
            binding!!.etPassword.requestFocus()
            Utils.showKeyboard(this)
        } else {
            stUsername = binding!!.etUsername.text.toString()
            stPassword = binding!!.etPassword.text.toString()
            loginUser()
        }
    }

    private fun loginUser() {
        if (Utils.isNetworkAvailable(this)) {
            Utils.hideKeyboard(this)
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val params = RequestParams()
            try {
                params.put("username", stUsername)
                params.put("password", stPassword)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "registerUser: " + e.message)
            }
            val communicator = Communicator()
            communicator.get(101, this, Constants.Apis.LOGIN, params,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onResponse: $response")
                    }

                    override fun onFailure(statusCode: Int, error: Throwable?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onFailure: $error")
                    }

                })
        } else {
            Utils.showToastPopup(this, resources.getString(R.string.internet_error))
        }
    }
}