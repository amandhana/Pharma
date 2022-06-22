package com.pharma.activity.home.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pharma.activity.home.activity.HomeActivity
import com.pharma.databinding.FragmentDiscountBinding

class DiscountFragment : Fragment(),View.OnClickListener {
    var binding: FragmentDiscountBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: DiscountFragment? = null
        fun newInstance(): DiscountFragment? {
            fragment = DiscountFragment()
            return fragment
        }

        fun getInstance(): DiscountFragment? {
            return if (fragment == null) DiscountFragment() else fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscountBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (mActivity as HomeActivity).resetBottom("discount")
    }

    private fun init() {
        mActivity = activity
        setUpClickListener()
    }
    private fun setUpClickListener(){
    }

    override fun onClick(view: View?) {
        val id = view?.id
    }

}