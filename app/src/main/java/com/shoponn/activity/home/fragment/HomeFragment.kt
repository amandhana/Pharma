package com.shoponn.activity.home.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.shoponn.R
import com.shoponn.activity.home.activity.HomeActivity
import com.shoponn.activity.home.adapter.*
import com.shoponn.activity.home.books.fragment.BooksFragment
import com.shoponn.activity.home.model.*
import com.shoponn.activity.home.newarrival.fragment.NewArrivalFragment
import com.shoponn.api.Communicator
import com.shoponn.api.Constants
import com.shoponn.api.CustomResponseListener
import com.shoponn.databinding.FragmentHomeBinding
import com.shoponn.support.Utils
import java.util.*
import kotlin.math.abs


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var responseData: List<String>
    private val TAG: String = "HomeFragment"
    var binding: FragmentHomeBinding? = null
    private var timer: Timer? = null
    var mActivity: Activity? = null
    private var rootView: View? = null

    private lateinit var progressDialog: ProgressDialog

    private var homePageBanner: List<HomePageBanner>? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: HomeFragment? = null
        fun newInstance(): HomeFragment? {
            fragment = HomeFragment()
            return fragment
        }

        fun getInstance(): HomeFragment? {
            return if (fragment == null) HomeFragment() else fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (mActivity as HomeActivity).resetBottom("home")
        //   (context as HomeActivity).showHideBottomBar(true)
        //     (context as HomeActivity).setHeaderTitle("Home")
    }

    private fun init() {
        mActivity = activity
        progressDialog = ProgressDialog(mActivity)
        setUpClickListener()
        setUpHomeSlider()
        setUpHomeCatTrending()
        startAutoScrollViewPager()
        setUpHomePharma()
        setUpFmcg()
        setUpBestDeals()
        setUpElectronics()
        setUpBeauty()
    }

    private fun setUpClickListener() {
        binding!!.recyclerViewCat.setOnClickListener(this)
    }

    private fun setUpHomeSlider() {
        /*Android Banner Data*/
        if (mActivity?.let { Utils.isNetworkAvailable(it) } == true) {
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val communicator = Communicator()
            communicator.get(101, mActivity!!, Constants.Apis.GETHOMEPAGEBANNERDETAILS,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: HomePageBannerDetailsResponse =
                                Utils.getObject(response, HomePageBannerDetailsResponse::class.java)
                                        as HomePageBannerDetailsResponse
                            if (!modelResponse.error!!) {
                                if (modelResponse.homePageBanner != null &&
                                    modelResponse.homePageBanner?.size!! > 0
                                ) {
                                    binding!!.autoScrollViewpager.adapter = HomeSliderAdapter(
                                        mActivity!!,
                                        modelResponse.homePageBanner
                                    )
                                    binding!!.pageIndicatorView.attachTo(binding!!.autoScrollViewpager)
                                    binding!!.autoScrollViewpager.offscreenPageLimit = 1
                                    val nextItemVisiblePx = 26
                                    val currentItemHorizontalMarginPx = 42
                                    val pageTranslationX =
                                        nextItemVisiblePx + currentItemHorizontalMarginPx
                                    val pageTransformer =
                                        ViewPager2.PageTransformer { page: View, position: Float ->
                                            page.translationX = -pageTranslationX * position
                                            page.scaleY =
                                                1 - (0.25f * abs(position))
                                        }
                                    binding!!.autoScrollViewpager.setOnPageClickListener { pager, position ->
                                        Log.e(
                                            TAG, "onResponse: Pager " +
                                                    "${modelResponse.homePageBanner?.get(position)?.url}"
                                        )
                                    }
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
        /*Android Banner Data*/
    }

    private fun startAutoScrollViewPager() {
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                binding!!.autoScrollViewpager.post {
                    binding!!.autoScrollViewpager.currentItem =
                        (binding!!.autoScrollViewpager.currentItem + 1) % 3
                }
            }
        }
        timer = Timer()
        timer!!.schedule(timerTask, 3000, 3000)
    }

    class HorizontalMarginItemDecoration(horizontalMarginInDp: Int) :
        RecyclerView.ItemDecoration() {
        private val horizontalMarginInPx: Int =
            horizontalMarginInDp

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }
    }

    @SuppressLint("InflateParams")
    private fun setUpHomeCatTrending() {

        var superCategories: ArrayList<HomeCatModel?>? = ArrayList()
        var superCatNames: List<String> =
            resources.getStringArray(R.array.super_categories_home).toList()
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
            binding!!.viewCatLay.removeAllViews()
            for (position in 0 until modelResponse.superCategories!!.size) {
                val inflater =
                    mActivity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                val itemView: View = inflater!!.inflate(R.layout.item_home_cat, null, true)

                val tvCatName = itemView.findViewById<TextView>(R.id.tv_cat_name)
                val ivCatImage = itemView.findViewById<ImageView>(R.id.iv_cat_image)

                ivCatImage.setBackgroundResource(modelResponse.superCategories!![position]!!.catImage)
                tvCatName.text = modelResponse.superCategories!![position]!!.catName

                tvCatName.tag = 1000 + position

                itemView.setOnClickListener {
                    val tag = tvCatName.tag as Int - 1000
                    Log.e(TAG, "setUpHomeCatTrending: $tag, ${superCategories!![position]?.catID}")
                    when (superCategories[position]?.catID) {
                        0 -> {
                            if (context is HomeActivity) {
                                (context as HomeActivity).resetBottom("home")
                                (context as HomeActivity).loadFragment(
                                    NewArrivalFragment::class.java.name,
                                    NewArrivalFragment.newInstance()!!,
                                    null
                                )
                            }
                        }
                        /*1 -> {
                            tvCatName.text = "Automotive, Real Estate & Industrial"
                            ivCatImage.setBackgroundResource(R.drawable.cat_real_estate)
                        }
                        2 -> {
                            tvCatName.text = "Toys, Kids & Babies"
                            ivCatImage.setBackgroundResource(R.drawable.cat_toys)
                        }
                        3 -> {
                            tvCatName.text = "Computers, Laptops & Gaming"
                            ivCatImage.setBackgroundResource(R.drawable.cat_computers)
                        }
                        4 -> {
                            tvCatName.text = "Gifts & Sweets"
                            ivCatImage.setBackgroundResource(R.drawable.cat_sweets)
                        }
                        5 -> {
                            tvCatName.text = "Mens Zone"
                            ivCatImage.setBackgroundResource(R.drawable.cat_mens_zone)
                        }
                        6 -> {
                            tvCatName.text = "Hobbies & E-Learning"
                            ivCatImage.setBackgroundResource(R.drawable.cat_hobbies)
                        }
                        7 -> {
                            tvCatName.text = "Tv, Audio, Cameras & Appliances"
                            ivCatImage.setBackgroundResource(R.drawable.cat_tv_camera)
                        }
                        8 -> {
                            tvCatName.text = "Home & Living"
                            ivCatImage.setBackgroundResource(R.drawable.cat_home)
                        }
                        9 -> {
                            tvCatName.text = "Mobiles, Tablets & Office Equipments"
                            ivCatImage.setBackgroundResource(R.drawable.cat_mobile)
                        }
                        10 -> {
                            tvCatName.text = "Womens Zone"
                            ivCatImage.setBackgroundResource(R.drawable.cat_womens_zone)
                        }
                        11 -> {
                            tvCatName.text = "Bags & Luggage"
                            ivCatImage.setBackgroundResource(R.drawable.cat_bags)
                        }
                        12 -> {
                            ivCatImage.setBackgroundResource(R.drawable.cat_jewellery)
                            tvCatName.text = "Jewellery & Gold"
                        }
                        13 -> {
                            tvCatName.text = "Sports & Fitness"
                            ivCatImage.setBackgroundResource(R.drawable.cat_sports_fitness)
                        }
                        14 -> {
                            ivCatImage.setBackgroundResource(R.drawable.cat_beauty)
                            tvCatName.text = "Beauty, Health & Fmcg"
                        }
                        16 -> {
                            tvCatName.text = "GST"
                            ivCatImage.setBackgroundResource(R.drawable.cat_gst)
                        }
                        17 -> {
                            tvCatName.text = "Work From Home"
                            ivCatImage.setBackgroundResource(R.drawable.cat_hobbies)
                        }
                        18 -> {
                            tvCatName.text = "Eateries"
                            ivCatImage.setBackgroundResource(R.drawable.cat_eateries)
                        }
                        19 -> {
                            tvCatName.text = "Prescription"
                            ivCatImage.setBackgroundResource(R.drawable.cat_prescription)
                        }
                        21 -> {
                            tvCatName.text = "Non-Prescription"
                            ivCatImage.setBackgroundResource(R.drawable.cat_2)
                        }
                        22 -> {
                            tvCatName.text = "Breakfast"
                            ivCatImage.setBackgroundResource(R.drawable.cat_breakfast)
                        }
                        23 -> {
                            tvCatName.text = "Brunch"
                            ivCatImage.setBackgroundResource(R.drawable.cat_brunch)
                        }
                        24 -> {
                            tvCatName.text = "Lunch"
                            ivCatImage.setBackgroundResource(R.drawable.cat_lunch)
                        }
                        25 -> {
                            tvCatName.text = "Dinner"
                            ivCatImage.setBackgroundResource(R.drawable.cat_dinner)
                        }*/
                        15 -> {
                            if (context is HomeActivity) {
                                (context as HomeActivity).resetBottom("home")
                                (context as HomeActivity).loadFragment(
                                    BooksFragment::class.java.name,
                                    BooksFragment.newInstance()!!,
                                    null
                                )
                            }
                        }
                    }
                }
                binding!!.viewCatLay.addView(itemView)
            }
        }
        /*binding!!.recyclerViewCat.layoutManager =
            LinearLayoutManager(
                mActivity,
                RecyclerView.HORIZONTAL,
                false
            )
        binding!!.recyclerViewCat.adapter = HomeCatAdapter(mActivity!!, modelResponse.superCategories)*/
    }

    private fun setUpHomePharma() {
        binding!!.recyclerViewPharma.layoutManager =
            LinearLayoutManager(
                mActivity,
                RecyclerView.HORIZONTAL,
                false
            )
        binding!!.recyclerViewPharma.adapter = HomePharmaAdapter(mActivity!!)
    }

    private fun setUpFmcg() {
        binding!!.recyclerViewFmcg.layoutManager =
            LinearLayoutManager(
                mActivity,
                RecyclerView.HORIZONTAL,
                false
            )
        binding!!.recyclerViewFmcg.adapter = HomeFmcgAdapter(mActivity!!)
    }

    private fun setUpBestDeals() {
        if (mActivity?.let { Utils.isNetworkAvailable(it) } == true) {
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val communicator = Communicator()
            communicator.get(101, mActivity!!, Constants.Apis.DEALOFTHEDAY,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: BestDealsResponse =
                                Utils.getObject(response, BestDealsResponse::class.java)
                                        as BestDealsResponse
                            if (!modelResponse.error!!) {
                                if (modelResponse.productItems != null &&
                                    modelResponse.productItems?.size!! > 0
                                ) {
                                    binding!!.recyclerViewBestDeals.layoutManager =
                                        LinearLayoutManager(
                                            mActivity,
                                            RecyclerView.HORIZONTAL,
                                            false
                                        )
                                    binding!!.recyclerViewBestDeals.adapter =
                                        HomeBestDealsAdapter(
                                            mActivity!!,
                                            modelResponse.productItems
                                        )
                                }
                            }
                        }
                    }

                    override fun onFailure(statusCode: Int, error: Throwable?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onFailure: $error")
                    }

                })

        } else
            mActivity?.let {
                Utils.showToastPopup(
                    it,
                    resources.getString(R.string.internet_error)
                )
            }
    }


    private fun setUpElectronics() {
        if (mActivity?.let { Utils.isNetworkAvailable(it) } == true) {
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val communicator = Communicator()
            communicator.get(101, mActivity!!, Constants.Apis.GETCATEGORYOFELECTRONICS,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: ElectronicsCategoryResponse =
                                Utils.getObject(response, ElectronicsCategoryResponse::class.java)
                                        as ElectronicsCategoryResponse
                            if (!modelResponse.error!!) {
                                if (modelResponse.categoryResponse != null &&
                                    modelResponse.categoryResponse?.size!! > 0
                                ) {
                                    binding!!.recyclerViewElectronics.layoutManager =
                                        LinearLayoutManager(
                                            mActivity,
                                            RecyclerView.HORIZONTAL,
                                            false
                                        )
                                    binding!!.recyclerViewElectronics.adapter =
                                        HomeElectronicsAdapter(
                                            mActivity!!,
                                            modelResponse.categoryResponse
                                        )
                                }
                            }
                        }
                    }

                    override fun onFailure(statusCode: Int, error: Throwable?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onFailure: $error")
                    }

                })

        } else
            mActivity?.let {
                Utils.showToastPopup(
                    it,
                    resources.getString(R.string.internet_error)
                )
            }
    }


    private fun setUpBeauty() {
        if (mActivity?.let { Utils.isNetworkAvailable(it) } == true) {
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val communicator = Communicator()
            communicator.get(101, mActivity!!, Constants.Apis.GETCATEGORYOFBEAUTY,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: BeautyCategoryResponse =
                                Utils.getObject(response, BeautyCategoryResponse::class.java)
                                        as BeautyCategoryResponse
                            if (!modelResponse.error!!) {
                                if (modelResponse.categoryResponse != null &&
                                    modelResponse.categoryResponse?.size!! > 0
                                ) {
                                    binding!!.recyclerViewBeautyFashion.layoutManager =
                                        LinearLayoutManager(
                                            mActivity,
                                            RecyclerView.HORIZONTAL,
                                            false
                                        )
                                    binding!!.recyclerViewBeautyFashion.adapter =
                                        HomeBeautyAdapter(
                                            mActivity!!,
                                            modelResponse.categoryResponse
                                        )
                                }
                            }
                        }
                    }

                    override fun onFailure(statusCode: Int, error: Throwable?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onFailure: $error")
                    }

                })

        } else
            mActivity?.let {
                Utils.showToastPopup(
                    it,
                    resources.getString(R.string.internet_error)
                )
            }
    }


    override fun onClick(view: View?) {
        val id = requireView().id
    }
}