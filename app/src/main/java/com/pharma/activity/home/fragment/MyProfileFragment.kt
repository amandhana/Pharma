package com.pharma.activity.home.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pharma.activity.home.activity.HomeActivity
import com.pharma.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment(),View.OnClickListener {
    var binding: FragmentMyProfileBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null

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
    }
    private fun setUpClickListener(){
    }

    override fun onClick(view: View?) {
        val id = requireView().id
    }

}