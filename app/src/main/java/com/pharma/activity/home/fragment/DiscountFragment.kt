package com.pharma.activity.home.fragment

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.pharma.activity.home.activity.HomeActivity
import com.pharma.databinding.FragmentDiscountBinding


class DiscountFragment : Fragment(),View.OnClickListener {
    var binding: FragmentDiscountBinding? = null
    var mActivity: Activity? = null
    private var rootView: View? = null

    private var superCatList: ArrayList<String> = ArrayList()

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
        superCatList.add("All")
        superCatList.add("Automotive, Real Estate & Industrial")
        superCatList.add("Toys ,Kids & Babies")
        superCatList.add("Computers ,Laptops & Gaming")
        superCatList.add("Gifts & Sweets")
        superCatList.add("Mens Zone")
        superCatList.add("Hobbies & E-Learning")
        superCatList.add("Tv, Audio, Cameras & Appliances")
        superCatList.add("Home & Living")
        superCatList.add("Mobiles ,Tablets & Office Equipments")
        superCatList.add("Womens Zone")
        superCatList.add("Bags & Luggage")
        superCatList.add("Jewellery & Gold")
        superCatList.add("Sports & Fitness")
        superCatList.add("Beauty, Health & Fmcg")
        superCatList.add("Books")
        superCatList.add("GST")
        superCatList.add("Work From Home")
        superCatList.add("Eateries")
        superCatList.add("Prescription")
        superCatList.add("Non-Prescription")
        superCatList.add("Breakfast")
        superCatList.add("Brunch")
        superCatList.add("Lunch")
        superCatList.add("Dinner")

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            R.layout.simple_spinner_item, superCatList.sorted()
        )
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding!!.superCatSpinner.adapter = spinnerAdapter

        setUpClickListener()
    }
    private fun setUpClickListener(){
    }

    override fun onClick(view: View?) {
        val id = view?.id
    }

}