package com.pharma.activity.home.activity

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.agot.support.Utils
import com.pharma.R
import com.pharma.activity.home.fragment.DiscountFragment
import com.pharma.activity.home.fragment.HomeFragment
import com.pharma.activity.home.fragment.MyCartFragment
import com.pharma.activity.home.fragment.MyProfileFragment
import com.pharma.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityHomeBinding? = null
    private var tag = ""
    private var isFirstTime: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    override fun onBackPressed() {
        val fragmentList = supportFragmentManager.fragments
        if (fragmentList.size > 0) {
            for (fragment in fragmentList) {
                if (fragment is HomeFragment)
                    exitAppPopup()
                else if (fragment is DiscountFragment) {
                    supportFragmentManager.popBackStack(
                        DiscountFragment::class.java.getName(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                } else if (fragment is MyProfileFragment) {
                    supportFragmentManager.popBackStack(
                        MyProfileFragment::class.java.getName(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }
            }
        }
    }


    private fun init() {
        mActivity = this
        setUpClickListener()
        loadFragment(HomeFragment::class.java.name, HomeFragment.newInstance()!!)
    }

    private fun setUpClickListener() {
        binding!!.homeLay.setOnClickListener(this)
        binding!!.discountLay.setOnClickListener(this)
        binding!!.myCartLay.setOnClickListener(this)
        binding!!.myProfileLay.setOnClickListener(this)

    }

    private fun loadFragment(tag: String, fragment: Fragment) {
        fragment.arguments = null
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentPrevious = supportFragmentManager.findFragmentByTag(tag)
        if (fragmentPrevious != null)
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        if (isFirstTime) {
            transaction.add(R.id.fragment_container, fragment)
        } else {
            transaction.replace(R.id.fragment_container, fragment)
        }
        if (!isFirstTime) {
            this.tag = tag
            transaction.addToBackStack(tag)
        } else {
            this.tag = tag
            isFirstTime = false
        }
        transaction.commit()
        supportFragmentManager.executePendingTransactions()
    }


    fun performNavMenuAction() {
        Utils.hideKeyboard(this)
        if (binding!!.drawerLayout.isDrawerOpen(GravityCompat.START)) binding!!.drawerLayout.closeDrawers() else binding!!.drawerLayout.openDrawer(
            GravityCompat.START
        )
    }

    fun exitAppPopup() {
        try {
            val dialog = Dialog(mActivity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_exit)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            val tvMessage = dialog.findViewById<TextView>(R.id.tv_message)
            tvMessage.text = getString(R.string.exit_message)
            val cvCancel: CardView = dialog.findViewById(R.id.cv_cancel)
            val cvOk: CardView = dialog.findViewById(R.id.cv_ok)
            cvCancel.setOnClickListener { v: View? -> dialog.dismiss() }
            cvOk.setOnClickListener { v: View? ->
                Utils.hideKeyboard(mActivity!!)
                finishAffinity()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                dialog.dismiss()
            }
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun resetBottom(type: String) {
        binding!!.tvHome.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.secondary_text_color
            )
        )
        binding!!.ivHome.background.setTint(
            ContextCompat.getColor(
                mActivity!!,
                R.color.secondary_text_color
            )
        )
        binding!!.tvDiscount.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.secondary_text_color
            )
        )
        binding!!.ivDiscount.background.setTint(
            ContextCompat.getColor(
                mActivity!!,
                R.color.secondary_text_color
            )
        )
        binding!!.tvMyCart.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.secondary_text_color
            )
        )
        binding!!.ivMyCart.background.setTint(
            ContextCompat.getColor(
                mActivity!!,
                R.color.secondary_text_color
            )
        )
        binding!!.tvMyProfile.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.secondary_text_color
            )
        )
        binding!!.ivMyProfile.background.setTint(
            ContextCompat.getColor(
                mActivity!!,
                R.color.secondary_text_color
            )
        )
        when (type) {
            "home" -> {
                binding!!.tvHome.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )
                binding!!.ivHome.background.setTint(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )
            }
            "discount" -> {
                binding!!.tvDiscount.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )
                binding!!.ivDiscount.background.setTint(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )
            }
            "my_cart" -> {
                binding!!.tvMyCart.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )
                binding!!.ivMyCart.background.setTint(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )
            }
            "my_profile" -> {
                binding!!.tvMyProfile.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )
                binding!!.ivMyProfile.background.setTint(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )

            }
        }
    }


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.home_lay) {
            resetBottom("home")
            loadFragment(HomeFragment::class.java.name, HomeFragment.newInstance()!!)
        } else if (id == R.id.my_cart_lay) {
            resetBottom("my_cart")
            loadFragment(MyCartFragment::class.java.name, MyCartFragment.newInstance()!!)
        } else if (id == R.id.discount_lay) {
            resetBottom("discount")
            loadFragment(DiscountFragment::class.java.name, DiscountFragment.newInstance()!!)
        } else if (id == R.id.my_profile_lay) {
            resetBottom("my_profile")
            loadFragment(MyProfileFragment::class.java.name, MyProfileFragment.newInstance()!!)
        }
    }


}