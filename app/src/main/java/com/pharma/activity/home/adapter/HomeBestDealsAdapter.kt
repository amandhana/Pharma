package com.pharma.activity.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharma.R
import com.pharma.activity.home.model.ProductItem
import com.pharma.databinding.ItemHomeBestDealsBinding
import com.pharma.support.Utils
import java.text.NumberFormat
import java.util.*

internal class HomeBestDealsAdapter(context: Activity, productItems: List<ProductItem?>?) :
    RecyclerView.Adapter<HomeBestDealsAdapter.ViewHolder>() {
    var context: Activity? = null
    var productItems: List<ProductItem?>? = null
    var binding: ItemHomeBestDealsBinding? = null

    init {
        this.context = context
        this.productItems = productItems
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemHomeBestDealsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }


    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {

        Utils.setImageUsingGlide(context,
            "https://seller.shoponn.co.in/${productItems!![position]?.imagePath2}",
            binding!!.ivCatImage)
        binding!!.tvMrp.text = NumberFormat.getCurrencyInstance(
            Locale("en", "in")
        ).format(
            productItems?.get(position)?.mRP)
        binding!!.tvMsp.text = NumberFormat.getCurrencyInstance(
            Locale("en", "in")
        ).format(
            productItems?.get(position)?.mSP)
        binding!!.tvCatName.text = productItems?.get(position)?.title
        binding!!.tvDiscount.text = "${productItems?.get(position)?.discountPercent.toString()} %"

        context?.resources?.getColor(R.color.white)
            ?.let { binding!!.tvDiscount.setTextColor(it) }
        context?.resources?.getColor(R.color.white)
            ?.let { binding!!.tvMsp.setTextColor(it) }
        context?.resources?.getColor(R.color.grey)
            ?.let { binding!!.tvMrp.setTextColor(it) }

        binding!!.tvMrp.paintFlags = binding!!.tvMrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }


    override fun getItemCount(): Int {
        return productItems?.size!!
    }

    class ViewHolder(binding: ItemHomeBestDealsBinding) : RecyclerView.ViewHolder(binding.root)
}