package com.pharma.activity.home.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.pharma.R
import com.pharma.activity.home.model.HomePageBanner
import com.pharma.support.Utils

class HomeSliderAdapter(
    context: Activity,
     homePageBanner: List<HomePageBanner?>?
) : PagerAdapter() {

    var context: Activity? = null
     var homePageBanner: List<HomePageBanner?>? = null
    var layoutInflater: LayoutInflater? = null

    init {
        this.context = context
         this.homePageBanner = homePageBanner
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return homePageBanner?.size!!
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(viewHolder: ViewGroup, position: Int): Any {
        val itemView: View =
            layoutInflater!!.inflate(R.layout.item_home_auto_scroll, viewHolder, false)
        val imageView = itemView.findViewById<ImageView>(R.id.iv_home_slider_image)
        homePageBanner?.let {
            Utils.setImageUsingGlide(context,
                "https://seller.shoponn.co.in/Image/${homePageBanner!![position]?.imgPath}",
                imageView)
        }
        viewHolder.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}