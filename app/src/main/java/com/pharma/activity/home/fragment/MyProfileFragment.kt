package com.pharma.activity.home.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.loopj.android.http.RequestParams
import com.pharma.R
import com.pharma.activity.home.activity.HomeActivity
import com.pharma.activity.home.adapter.HomeSliderAdapter
import com.pharma.activity.home.model.HomePageBannerDetailsResponse
import com.pharma.activity.profile.model.MyProfileResponse
import com.pharma.api.Communicator
import com.pharma.api.Constants
import com.pharma.api.CustomResponseListener
import com.pharma.databinding.FragmentMyProfileBinding
import com.pharma.support.Preference
import com.pharma.support.Utils

class MyProfileFragment : Fragment(),View.OnClickListener {

    private val TAG: String = "MyProfileFragment"
    var binding: FragmentMyProfileBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null
    var preference: Preference? = null

    private lateinit var progressDialog: ProgressDialog

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: MyProfileFragment? = null
        fun newInstance(): MyProfileFragment? {
            fragment = MyProfileFragment()
            return fragment
        }

        fun getInstance(): MyProfileFragment? {
            return if (fragment == null) MyProfileFragment() else fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyProfileBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }
    override fun onResume() {
        super.onResume()
        (mActivity as HomeActivity).resetBottom("my_profile")
    }

    private fun init() {
        mActivity = activity
        progressDialog = ProgressDialog(mActivity)
        preference = Preference().getInstance(requireContext())
        getCustomerProfile()
        binding!!.customerName.text = String.format("%s %s", preference?.getString("FirstName"),
            preference?.getString("LastName"))
        binding!!.customerEmail.text = preference?.getString("EmailId")
    }

    private fun getCustomerProfile() {
        if (mActivity?.let { Utils.isNetworkAvailable(it) } == true) {
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            var params = RequestParams()
            try {
                params.put("useremailid", preference?.getString("EmailId"))
                params.put("password", preference?.getString("password"))
            } catch (e: Exception) {
                Log.e(TAG, "getCustomerProfile: $e")
            }
            Log.e(TAG, "getCustomerProfile: CustomerID ${preference?.getString("CustomerRegId")}")
            val communicator = Communicator()
            communicator.get(101, mActivity!!, Constants.Apis.GETCUSTOMERADDRESS, params,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: MyProfileResponse =
                                Utils.getObject(response, MyProfileResponse::class.java)
                                        as MyProfileResponse
                            if (!modelResponse.error!!) {
                                if (modelResponse.customerAddressResponse != null &&
                                    modelResponse.customerAddressResponse?.size!! > 0) {
                                    var address = ""
                                    for (childResponse in modelResponse.customerAddressResponse!!) {
                                        binding!!.customerMobile.text = childResponse?.mobileNo
                                        if (childResponse?.addressLine1 != null &&
                                            childResponse.addressLine1!!.isNotEmpty()) {
                                            address = "$address ${childResponse.addressLine1}\n"
                                        }
                                        if (childResponse?.addressLine2 != null &&
                                            childResponse.addressLine2!!.isNotEmpty()) {
                                            address = "$address ${childResponse.addressLine2}\n"
                                        }
                                        if (childResponse?.landmark != null &&
                                            childResponse.landmark!!.isNotEmpty()) {
                                            address = "$address ${childResponse.landmark}\n"
                                        }
                                        if (childResponse?.city != null &&
                                            childResponse.city!!.isNotEmpty()) {
                                            address = "$address ${childResponse.city}\n"
                                        }
                                        if (childResponse?.state != null &&
                                            childResponse.state!!.isNotEmpty()) {
                                            address = "$address ${childResponse.state}\n"
                                        }
                                        if (childResponse?.countryName != null &&
                                            childResponse.countryName!!.isNotEmpty()) {
                                            address = "$address ${childResponse.countryName}\n"
                                        }
                                        if (childResponse?.pincode != null &&
                                            childResponse.pincode!!.isNotEmpty()) {
                                            address = " - $address ${childResponse.pincode}"
                                        }
                                    }
                                    binding!!.customerAddress.text = address
                                }
                            }
                        }
                    }

                    override fun onFailure(statusCode: Int, error: Throwable?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onFailure: $error")
                    }
                })
        } else {
            mActivity?.let {
                Utils.showToastPopup(
                    it,
                    resources.getString(R.string.internet_error)
                )
            }
        }
    }

    private fun setUpClickListener(){
    }

    override fun onClick(view: View?) {
        val id = requireView().id
    }

}