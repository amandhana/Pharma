package com.pharma.activity.home.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pharma.R
import com.pharma.activity.home.activity.HomeActivity
import com.pharma.databinding.FragmentMyCartBinding

class MyCartFragment : Fragment(), View.OnClickListener {
    var binding: FragmentMyCartBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: MyCartFragment? = null
        fun newInstance(): MyCartFragment? {
            fragment = MyCartFragment()
            return fragment
        }

        fun getInstance(): MyCartFragment? {
            return if (fragment == null) MyCartFragment() else fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCartBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (mActivity as HomeActivity).resetBottom("my_cart")
    }

    private fun init() {
        mActivity = activity
        setUpClickListener()
    }
    private fun setUpClickListener(){
        binding!!.btnContinueShopping.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.btn_continue_shopping) {
            (mActivity as HomeActivity).resetBottom("home")
            (mActivity as HomeActivity).loadFragment(
                HomeFragment::class.java.name, HomeFragment.newInstance()!!)
        }
    }

}