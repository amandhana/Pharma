package com.pharma.activity.welcome.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.pharma.R

class WelcomeSliderAdapter(
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
            layoutInflater!!.inflate(R.layout.item_welcome_screen, viewHolder, false)
        val imageView = itemView.findViewById<ImageView>(R.id.iv_welcome_image)
        when (position) {
           /* 0 -> {
                imageView.setBackgroundResource(R.drawable.ic_welcome_slide_1)
            }
            1 -> {
                imageView.setBackgroundResource(R.drawable.ic_welcome_slide_2)
            }
            2 -> {
                imageView.setBackgroundResource(R.drawable.ic_welcome_slide_3)
            }*/
        }
        viewHolder.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}