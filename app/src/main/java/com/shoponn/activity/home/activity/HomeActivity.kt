package com.shoponn.activity.home.activity

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
import com.loopj.android.http.RequestParams
import com.shoponn.R
import com.shoponn.activity.home.adapter.HomeNavDrawerAdapter
import com.shoponn.activity.home.books.fragment.BooksFragment
import com.shoponn.activity.home.books.model.BooksResponse
import com.shoponn.activity.home.books.model.CategoryResponse
import com.shoponn.activity.home.fragment.DiscountFragment
import com.shoponn.activity.home.fragment.HomeFragment
import com.shoponn.activity.home.fragment.MyCartFragment
import com.shoponn.activity.home.fragment.MyProfileFragment
import com.shoponn.activity.home.model.HomeCatModel
import com.shoponn.activity.home.model.sub_cat.books.BooksSubCatResponse
import com.shoponn.activity.home.newarrival.fragment.NewArrivalFragment
import com.shoponn.api.Communicator
import com.shoponn.api.Constants
import com.shoponn.api.CustomResponseListener
import com.shoponn.databinding.ActivityHomeBinding
import com.shoponn.support.Utils


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG: String = "HomeActivity"
    var mActivity: Activity? = null
    var binding: ActivityHomeBinding? = null
    private var tag = ""
    private var isFirstTime: Boolean = true
    private var superCategories: ArrayList<HomeCatModel?>? = null
    private lateinit var superCatNames: List<String>
    private val responseData: ArrayList<String> = ArrayList()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        superCategories = ArrayList()
        superCatNames = resources.getStringArray(R.array.super_cat_home_spinner).toList()

        init()
    }

    fun setUpDrawerItems(categoriesResponse: List<CategoryResponse?>?) {
        Log.e(TAG, "setUpDrawerItems: $categoriesResponse")
        for (item in categoriesResponse!!) {
            Log.e(TAG, "onResponse: ${item?.category}")
            item?.category?.let { responseData.add(it) }
        }
        val adapter = HomeNavDrawerAdapter(this@HomeActivity, responseData)
        binding!!.leftMenuLay.lvCategories.adapter = adapter
        binding!!.leftMenuLay.lvCategories.setOnItemClickListener { parent, view, position, id ->
            Log.e(TAG, "setUpDrawerItems: ${categoriesResponse[position]?.categoryID}")
            showBooksSubCatData(categoriesResponse[position]?.categoryID)
        }
        binding!!.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun showBooksSubCatData(categoryID: Int?) {
        Log.e(TAG, "showBooksSubCatData: $categoryID")
        if (mActivity?.let { Utils.isNetworkAvailable(it) } == true) {
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val params = RequestParams()
            try {
                params.put("catID", categoryID!!)
            } catch (e: Exception) {
                Log.e(TAG, "showBooksSubCatData: $e")
            }
            val communicator = Communicator()
            communicator.get(101, mActivity!!, Constants.Apis.GETSUBCATBOOKS, params,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        Log.e(TAG, "onResponse: $response")
                        progressDialog.dismiss()
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: BooksSubCatResponse = Utils.getObject(
                                response, BooksSubCatResponse::class.java)
                                    as BooksSubCatResponse
                            if (!modelResponse.error!!) {
                                if (modelResponse.subCategoryResponse != null &&
                                    modelResponse.subCategoryResponse!!.isNotEmpty()) {

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

    /*@SuppressLint("InflateParams")
    private fun setUpHomeCatSpinner() {
        val gson = Gson()
        for (item in superCatNames) {
            if (item == "New Arrival") {
                superCategories!!.add(HomeCatModel(0, item, R.drawable.cat_1))
            } else if (item == "Automotive, Real Estate & Industrial") {
                superCategories!!.add(HomeCatModel(1, item, R.drawable.cat_real_estate))
            } else if (item == "Bags & Luggage") {
                superCategories!!.add(HomeCatModel(11, item, R.drawable.cat_bags))
            } else if (item == "Beauty, Health & Fmcg") {
                superCategories!!.add(HomeCatModel(14, item, R.drawable.cat_beauty))
            } else if (item == "Books") {
                superCategories!!.add(HomeCatModel(15, item, R.drawable.cat_books))
            } else if (item == "Breakfast") {
                superCategories!!.add(HomeCatModel(22, item, R.drawable.cat_breakfast))
            } else if (item == "Brunch") {
                superCategories!!.add(HomeCatModel(23, item, R.drawable.cat_brunch))
            } else if (item == "Computers, Laptops & Gaming") {
                superCategories!!.add(HomeCatModel(3, item, R.drawable.cat_computers))
            } else if (item == "Dinner") {
                superCategories!!.add(HomeCatModel(25, item, R.drawable.cat_dinner))
            } else if (item == "Eateries") {
                superCategories!!.add(HomeCatModel(18, item, R.drawable.cat_eateries))
            } else if (item == "Gifts & Sweets") {
                superCategories!!.add(HomeCatModel(4, item, R.drawable.cat_sweets))
            } else if (item == "GST") {
                superCategories!!.add(HomeCatModel(16, item, R.drawable.cat_gst))
            } else if (item == "Hobbies & E-Learning") {
                superCategories!!.add(HomeCatModel(6, item, R.drawable.cat_hobbies))
            } else if (item == "Home & Living") {
                superCategories!!.add(HomeCatModel(8, item, R.drawable.cat_home))
            } else if (item == "Jewellery & Gold") {
                superCategories!!.add(HomeCatModel(12, item, R.drawable.cat_jewellery))
            } else if (item == "Lunch") {
                superCategories!!.add(HomeCatModel(24, item, R.drawable.cat_lunch))
            } else if (item == "Mens Zone") {
                superCategories!!.add(HomeCatModel(5, item, R.drawable.cat_mens_zone))
            } else if (item == "Mobiles, Tablets & Office Equipments") {
                superCategories!!.add(HomeCatModel(9, item, R.drawable.cat_mobile))
            } else if (item == "Non-Prescription") {
                superCategories!!.add(HomeCatModel(21, item, R.drawable.cat_prescription))
            } else if (item == "Prescription") {
                superCategories!!.add(HomeCatModel(19, item, R.drawable.cat_prescription))
            } else if (item == "Sports & Fitness") {
                superCategories!!.add(HomeCatModel(13, item, R.drawable.cat_sports_fitness))
            } else if (item == "Toys, Kids & Babies") {
                superCategories!!.add(HomeCatModel(2, item, R.drawable.cat_toys))
            } else if (item == "Tv, Audio, Cameras & Appliances") {
                superCategories!!.add(HomeCatModel(7, item, R.drawable.cat_tv_camera))
            } else if (item == "Womens Zone") {
                superCategories!!.add(HomeCatModel(10, item, R.drawable.cat_womens_zone))
            } else if (item == "Work From Home") {
                superCategories!!.add(HomeCatModel(17, item, R.drawable.cat_work_from_home))
            }
        }
        val dataDict = mapOf(
            "Message" to "Super Categories result return successfully!",
            "superCategories" to superCategories
        )
        val jsonString = gson.toJson(dataDict)
        Log.e(TAG, "setUpHomeCatTrending: $jsonString")
        val modelResponse: HomeCatResponse =
            Utils.getObject(jsonString, HomeCatResponse::class.java) as HomeCatResponse
        if (modelResponse.superCategories != null && modelResponse.superCategories!!.isNotEmpty()) {
            setSpinnerData(superCatNames)
        }
    }

    private fun setSpinnerData(superCatNames: List<String>) {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.item_spinner, R.id.tv_cat_name_spinner, superCatNames
        )
        binding!!.catSpinner.adapter = adapter
        binding!!.catSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                Log.e(TAG, "onItemSelected: ${binding!!.catSpinner.selectedItem}")
                when (position) {
                    4 -> {
                        getBooksCat()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }*/

    private fun getBooksCat() {

        if (mActivity?.let { Utils.isNetworkAvailable(it) } == true) {
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val communicator = Communicator()
            communicator.get(101, mActivity!!, Constants.Apis.GETCATEGORYOFBOOKS,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: BooksResponse =
                                Utils.getObject(response, BooksResponse::class.java)
                                        as BooksResponse
                            if (!modelResponse.error!!) {
                                if (modelResponse.categoryResponse != null &&
                                    modelResponse.categoryResponse?.size!! > 0) {
                                    setUpDrawerItems(modelResponse.categoryResponse)
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

    override fun onBackPressed() {
        val fragmentList = supportFragmentManager.fragments
        if (fragmentList.size > 0) {
            for (fragment in fragmentList) {
                if (fragment is HomeFragment)
                    exitAppPopup()
                else if (fragment is DiscountFragment) {
                    supportFragmentManager.popBackStack(
                        DiscountFragment::class.java.name,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                } else if (fragment is NewArrivalFragment) {
                    supportFragmentManager.popBackStack(
                        NewArrivalFragment::class.java.name,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                } else if (fragment is BooksFragment) {
                    supportFragmentManager.popBackStack(
                        BooksFragment::class.java.name,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                } else if (fragment is MyCartFragment) {
                    supportFragmentManager.popBackStack(
                        MyCartFragment::class.java.name,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                } else if (fragment is MyProfileFragment) {
                    supportFragmentManager.popBackStack(
                        MyProfileFragment::class.java.name,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }
            }
        }
    }


    private fun init() {
        mActivity = this
        progressDialog = ProgressDialog(mActivity)
        setUpClickListener()
        // setUpHomeCatSpinner()
        loadFragment(HomeFragment::class.java.name, HomeFragment.newInstance()!!, null)
    }

    private fun setUpClickListener() {
        binding!!.homeLay.setOnClickListener(this)
        binding!!.discountLay.setOnClickListener(this)
        binding!!.myCartLay.setOnClickListener(this)
        binding!!.myProfileLay.setOnClickListener(this)
        binding!!.ivMenu.setOnClickListener(this)
        binding!!.ivSearch.setOnClickListener(this)
        binding!!.ivAudioSearch.setOnClickListener(this)

    }

    fun loadFragment(tag: String, fragment: Fragment, bundle: Bundle?) {
        fragment.arguments = null
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentPrevious = supportFragmentManager.findFragmentByTag(tag)
        if (bundle != null) {
            fragment.arguments = bundle
        }
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
        if (binding!!.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding!!.drawerLayout.closeDrawers()
        else
            binding!!.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun exitAppPopup() {
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
        /*binding!!.ivDiscount.background.setTint(
            ContextCompat.getColor(
                mActivity!!,
                R.color.secondary_text_color
            )
        )*/
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

                binding!!.headerLay.visibility = View.VISIBLE
            }
            "discount" -> {
                binding!!.tvDiscount.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )
                /*binding!!.ivDiscount.background.setTint(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )*/

                binding!!.headerLay.visibility = View.GONE
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

                binding!!.headerLay.visibility = View.GONE
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

                binding!!.headerLay.visibility = View.GONE

            }
        }
    }


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.home_lay) {
            resetBottom("home")
            loadFragment(HomeFragment::class.java.name, HomeFragment.newInstance()!!, null)
        } else if (id == R.id.my_cart_lay) {
            resetBottom("my_cart")
            loadFragment(MyCartFragment::class.java.name, MyCartFragment.newInstance()!!, null)
        } else if (id == R.id.discount_lay) {
            resetBottom("discount")
            loadFragment(DiscountFragment::class.java.name, DiscountFragment.newInstance()!!, null)
        } else if (id == R.id.my_profile_lay) {
            resetBottom("my_profile")
            loadFragment(MyProfileFragment::class.java.name, MyProfileFragment.newInstance()!!, null)
        } else if (id == R.id.iv_menu) {
            performNavMenuAction()
        } else if (id == R.id.iv_search) {
            Log.e(TAG, "onClick: Working Search")
        } else if (id == R.id.iv_audio_search) {
            Log.e(TAG, "onClick: Working Audio Search")
        }
    }


}