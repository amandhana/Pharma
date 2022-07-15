package com.shoponn.activity.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.shoponn.R


class HomeNavDrawerAdapter(context: Activity, categories: List<String>) :
    BaseAdapter() {
    var context: Activity? = null
    private var categories: List<String>
    var layoutInflater: LayoutInflater? = null

    init {
        this.context = context
        this.categories = categories
        this.layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return categories.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup): View {
        var view = convertView
        view = layoutInflater!!.inflate(
            R.layout.item_left_menu, null
        )
        val title = view.findViewById<TextView>(R.id.tv_cat_name)
        title!!.text = categories[position].trim()
        return view
    }

}