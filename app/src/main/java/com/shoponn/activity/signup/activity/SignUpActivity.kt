package com.shoponn.activity.signup.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.loopj.android.http.RequestParams
import com.shoponn.R
import com.shoponn.activity.login.activity.LoginActivity
import com.shoponn.activity.signup.model.GetMaxCustomerIDResponse
import com.shoponn.activity.signup.model.RegisterResponse
import com.shoponn.api.Communicator
import com.shoponn.api.Constants
import com.shoponn.api.CustomResponseListener
import com.shoponn.support.Utils
import com.shoponn.views.MediumEditText
import com.shoponn.views.RegularTextView
import java.text.SimpleDateFormat
import java.util.*


class SignUpActivity : AppCompatActivity() {

    private val TAG = "SignUpActivity"
    private val mActivity: Activity = this

    private lateinit var etFirstName: MediumEditText
    private lateinit var etLastName: MediumEditText
    private lateinit var etMobile: MediumEditText
    private lateinit var etEmail: MediumEditText
    private lateinit var etPassword: MediumEditText
    private lateinit var etConfirmPassword: MediumEditText

    private lateinit var btnSignUp: CardView
    private lateinit var btnLogin: RegularTextView

    private lateinit var stFirstName: String
    private lateinit var stLastName: String
    private lateinit var stMobile: String
    private lateinit var stEmail: String
    private lateinit var stPassword: String
    private lateinit var stConfirmPassword: String
    private var customerMaxIDResponse: Int = 0

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        init()
        getCustomerID()

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnSignUp.setOnClickListener {
            register()
        }

    }

    private fun register() {
        stFirstName = etFirstName.text.toString()
        stLastName = etLastName.text.toString()
        stMobile = etMobile.text.toString()
        stEmail = etEmail.text.toString()
        stPassword = etPassword.text.toString()
        stConfirmPassword = etConfirmPassword.text.toString()

        if (stFirstName.isEmpty()) {
            etFirstName.error = "First Name can't be empty!"
            etFirstName.requestFocus()
            Utils.showKeyboard(this)
        } else if (stLastName.isEmpty()) {
            etLastName.error = "Last Name can't be empty!"
            etLastName.requestFocus()
            Utils.showKeyboard(this)
        } else if (stMobile.isEmpty()) {
            etMobile.error = "Mobile Number can't be empty!"
            etMobile.requestFocus()
            Utils.showKeyboard(this)
        } else if (stEmail.isEmpty()) {
            etEmail.error = "Email can't be empty!"
            etEmail.requestFocus()
            Utils.showKeyboard(this)
        } else if (stPassword.isEmpty()) {
            etPassword.error = "Password can't be empty!"
            etPassword.requestFocus()
            Utils.showKeyboard(this)
        } else if (stConfirmPassword.isEmpty()) {
            etConfirmPassword.error = "Confirm Password can't be empty!"
            etConfirmPassword.requestFocus()
            Utils.showKeyboard(this)
        } else if (stMobile.length < 10) {
            etMobile.error = "Enter a valid 10 digit Mobile Number!"
            etMobile.requestFocus()
            Utils.showKeyboard(this)
        } else if (!Utils.isValidEmail(stEmail)) {
            etEmail.error = "Enter a valid Email!"
            etEmail.requestFocus()
            Utils.showKeyboard(this)
        } else if (stConfirmPassword != stPassword) {
            etConfirmPassword.error = "Password does not match!"
            etConfirmPassword.requestFocus()
            Utils.showKeyboard(this)
        } else {
            registerUser()
        }
    }

    private fun getCustomerID() {
        if (Utils.isNetworkAvailable(this)) {
            Utils.hideKeyboard(this)
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val communicator = Communicator()
            communicator.get(101, this, Constants.Apis.GETCUSTOMERID,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onResponse: $response")
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: GetMaxCustomerIDResponse = Utils.getObject(response,
                                GetMaxCustomerIDResponse::class.java) as GetMaxCustomerIDResponse
                            customerMaxIDResponse = modelResponse.customerMaxIDResponse?.get(0)?.customerMaxRegId!!
                        }
                    }

                    override fun onFailure(statusCode: Int, error: Throwable?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onFailure: $error")
                        Utils.showToastPopup(mActivity,
                            "Unable to Get Customer ID, Please Try Again!")
                    }
                })
        } else {
            Utils.showToastPopup(this, resources.getString(R.string.internet_error))
        }
    }

    private fun registerUser() {
        if (Utils.isNetworkAvailable(this)) {
            Utils.hideKeyboard(this)
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val params = RequestParams()
            try {
                params.put("custID", customerMaxIDResponse)
                params.put("firstName", stFirstName)
                params.put("lastName", stLastName)
                params.put("titleID", 1)
                params.put("mobNo", stMobile)
                params.put("emailAddess", stEmail)
                params.put("password", stPassword)
                params.put("adddatetime", getCurrentDateTime())
                params.put("adduserid", 1)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "registerUser: " + e.message)
            }
            val communicator = Communicator()
            communicator.post(101, this, Constants.Apis.REGISTER,
                params, object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onResponse: $response")
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: RegisterResponse = Utils.getObject(response,
                                RegisterResponse::class.java) as RegisterResponse
                            if (!modelResponse.error!!) {
                                Toast.makeText(mActivity,
                                    "${modelResponse.message.toString()}, Please Login to Continue.",
                                    Toast.LENGTH_SHORT).show()
                                mActivity.let { Utils.startActivityFinish(it, LoginActivity::class.java) }
                                /*mActivity.let { Utils.startActivityFinish(it, OtpActivity::class.java) }*/
                            } else {
                                Utils.showToastPopup(mActivity,
                                    "Unable to Create the account at the moment, Please Try Again!")
                                Log.e(TAG, "onResponse: ${modelResponse.message}")
                            }
                        } else {
                            Utils.showToastPopup(mActivity, "Something went wrong, Please Try Again!")
                        }
                    }

                    override fun onFailure(statusCode: Int, error: Throwable?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onFailure: $error")
                        Utils.showToastPopup(mActivity,
                            "Unable to Create the account at the moment, Please Try Again!")
                        Log.e(TAG, "onResponse: ${error?.message}")
                    }

                })
        } else {
            Utils.showToastPopup(this, resources.getString(R.string.internet_error))
        }
    }

    private fun getCurrentDateTime(): String? {
        return  SimpleDateFormat("yyyy-MM-dd%HH:mm:ss", Locale.getDefault()).format(Date())
    }

    private fun init() {
        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        etMobile = findViewById(R.id.et_phone)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)

        btnSignUp = findViewById(R.id.cv_sign_up)
        btnLogin = findViewById(R.id.tv_login)

        progressDialog = ProgressDialog(this)
    }

}