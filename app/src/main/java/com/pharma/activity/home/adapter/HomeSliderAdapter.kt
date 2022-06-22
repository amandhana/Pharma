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

class HomeSliderAdapter(
    context: Activity
) : PagerAdapter() {

    var context: Activity? = null
    var layoutInflater: LayoutInflater? = null

    init {
        this.context = context
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(viewHolder: ViewGroup, position: Int): Any {
        val itemView: View =
            layoutInflater!!.inflate(R.layout.item_home_auto_scroll, viewHolder, false)
        val imageView = itemView.findViewById<ImageView>(R.id.iv_home_slider_image)
        when (position) {
            0 -> {
                imageView.setBackgroundResource(R.drawable.slide_1)
            }
            1 -> {
                imageView.setBackgroundResource(R.drawable.banner_1)
            }
            2 -> {
                imageView.setBackgroundResource(R.drawable.banner_2)
            }
        }
        viewHolder.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}