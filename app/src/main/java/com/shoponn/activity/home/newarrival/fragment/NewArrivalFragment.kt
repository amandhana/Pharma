package com.shoponn.activity.home.newarrival.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shoponn.R
import com.shoponn.activity.home.activity.HomeActivity
import com.shoponn.activity.home.fragment.HomeFragment
import com.shoponn.activity.home.newarrival.adapter.NewArrivalAdapter
import com.shoponn.activity.home.newarrival.model.NewArrivalResponse
import com.shoponn.api.Communicator
import com.shoponn.api.Constants
import com.shoponn.api.CustomResponseListener
import com.shoponn.databinding.FragmentNewArrivalBinding
import com.shoponn.support.Utils

class NewArrivalFragment : Fragment(), View.OnClickListener {

    private val TAG: String = "NewArrivalFragment"
    var binding: FragmentNewArrivalBinding? = null
    var mActivity: Activity? = null
    private var rootView: View? = null

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewArrivalBinding.inflate(inflater)
        rootView = binding!!.root
        init()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (mActivity as HomeActivity).resetBottom("home")
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: NewArrivalFragment? = null

        @JvmStatic
        fun newInstance(): NewArrivalFragment? {
            fragment = NewArrivalFragment()
            return fragment
        }

        fun getInstance(): HomeFragment? {
            return if (HomeFragment.fragment == null) HomeFragment() else HomeFragment.fragment
        }
    }

    fun init() {
        mActivity = activity
        progressDialog = ProgressDialog(mActivity)
        setUpClickListener()
        showNewArrivals()
    }

    private fun showNewArrivals() {
        if (mActivity?.let { Utils.isNetworkAvailable(it) } == true) {
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val communicator = Communicator()
            communicator.get(101, mActivity!!, Constants.Apis.NEWLYLAUNCHED,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: NewArrivalResponse =
                                Utils.getObject(response, NewArrivalResponse::class.java)
                                        as NewArrivalResponse
                            if (!modelResponse.error!!) {
                                if (modelResponse.productItems != null &&
                                    modelResponse.productItems?.size!! > 0) {
                                    binding!!.tvNoData.visibility = View.GONE
                                    binding!!.rvNewArrival.visibility = View.VISIBLE
                                    binding!!.rvNewArrival.layoutManager =
                                        GridLayoutManager(
                                            mActivity,
                                            2,
                                            RecyclerView.VERTICAL,
                                            false
                                        )
                                    binding!!.rvNewArrival.adapter =
                                        NewArrivalAdapter(mActivity!!, modelResponse.productItems)
                                } else {
                                    binding!!.tvNoData.visibility = View.VISIBLE
                                    binding!!.rvNewArrival.visibility = View.GONE
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

    private fun setUpClickListener() {

    }

    override fun onClick(view: View?) {

    }
}