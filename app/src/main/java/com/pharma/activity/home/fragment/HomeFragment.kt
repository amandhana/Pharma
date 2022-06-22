package com.pharma.activity.home.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.pharma.activity.home.activity.HomeActivity
import com.pharma.activity.home.adapter.*
import com.pharma.activity.home.adapter.HomeBestDealsAdapter
import com.pharma.activity.home.adapter.HomeCatAdapter
import com.pharma.activity.home.adapter.HomeFmcgAdapter
import com.pharma.activity.home.adapter.HomePharmaAdapter
import com.pharma.databinding.FragmentHomeBinding
import java.util.*


class HomeFragment : Fragment(), View.OnClickListener {
    var binding: FragmentHomeBinding? = null
    var timer: Timer? = null
    var mActivity: Activity? = null
    var rootView: View? = null

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
    }

    private fun setUpHomeSlider() {
        binding!!.autoScrollViewpager.adapter = HomeSliderAdapter(mActivity!!)
        binding!!.pageIndicatorView.attachTo(binding!!.autoScrollViewpager)
        binding!!.autoScrollViewpager.offscreenPageLimit = 1
        val nextItemVisiblePx = 26
        val currentItemHorizontalMarginPx = 42
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
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