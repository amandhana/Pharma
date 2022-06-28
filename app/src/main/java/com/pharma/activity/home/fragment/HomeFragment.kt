package com.pharma.activity.home.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.pharma.R
import com.pharma.activity.home.activity.HomeActivity
import com.pharma.activity.home.adapter.*
import com.pharma.activity.home.model.HomePageBanner
import com.pharma.activity.home.model.HomePageBannerDetailsResponse
import com.pharma.api.Communicator
import com.pharma.api.Constants
import com.pharma.api.CustomResponseListener
import com.pharma.databinding.FragmentHomeBinding
import com.pharma.support.Utils
import java.util.*


class HomeFragment : Fragment(), View.OnClickListener {

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
    }

    private fun setUpClickListener() {
        binding!!.recyclerViewCat.setOnClickListener(this)
    }

    private fun setUpHomeSlider() {
        /*Android Banner Data*/
        /*if (mActivity?.let { Utils.isNetworkAvailable(it) } == true) {
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val communicator = Communicator()
            communicator.get(101, mActivity!!, Constants.Apis.GETHOMEPAGEBANNERDETAILS,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onResponse: $response")
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: HomePageBannerDetailsResponse =
                                Utils.getObject(response, HomePageBannerDetailsResponse::class.java)
                                        as HomePageBannerDetailsResponse
                            if (!modelResponse.error!!) {
                                if (modelResponse.homePageBanner != null) {
                                    modelResponse.homePageBanner!!.forEach {
                                        homePageBanner = homePageBanner?.plus(
                                            HomePageBanner(
                                                it?.alternateText,
                                                it?.bannerID, it?.imgPath, it?.url
                                            )
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
        }*/
        /*Android Banner Data*/

        binding!!.autoScrollViewpager.adapter =HomeSliderAdapter(mActivity!!)
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
                    1 - (0.25f * kotlin.math.abs(position))
            }
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

    private fun setUpHomeCatTrending() {
        binding!!.recyclerViewCat.layoutManager =
            LinearLayoutManager(
                mActivity,
                RecyclerView.HORIZONTAL,
                false
            )
        binding!!.recyclerViewCat.adapter = HomeCatAdapter(mActivity!!)
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
        binding!!.recyclerViewBestDeals.layoutManager =
            LinearLayoutManager(
                mActivity,
                RecyclerView.HORIZONTAL,
                false
            )
        binding!!.recyclerViewBestDeals.adapter = HomeBestDealsAdapter(mActivity!!)
    }


    private fun setUpElectronics() {
        binding!!.recyclerViewElectronics.layoutManager =
            LinearLayoutManager(
                mActivity,
                RecyclerView.HORIZONTAL,
                false
            )
        binding!!.recyclerViewElectronics.adapter = HomeElecrtonicsAdapter(mActivity!!)
    }


    override fun onClick(view: View?) {
        val id = requireView().id
    }
}