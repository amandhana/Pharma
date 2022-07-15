package com.shoponn.activity.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.shoponn.R
import com.shoponn.activity.home.model.HomeCatModel

class CategoriesSpinnerAdapter(context: Activity, list : List<HomeCatModel?>?) : BaseAdapter() {
    var context: Activity? = null
    var list : List<HomeCatModel?>? = null
    var inflter: LayoutInflater? = null
    init {
        this.context = context
        this.list = list
        inflter = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return list!!.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var view1 = view
        view1 = inflter!!.inflate(R.layout.item_spinner, null)
        val tvName = view1!!.findViewById<TextView>(R.id.tv_cat_name_spinner)
        tvName.text = list!![position]!!.catName
        return view1
    }
}