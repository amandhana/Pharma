package com.pharma.activity.home.newarrival.adapter

import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharma.R
import com.pharma.activity.home.newarrival.model.ProductItem
import com.pharma.databinding.ItemNewArrivalCatBinding
import com.pharma.support.Utils
import java.text.NumberFormat
import java.util.*

internal class NewArrivalAdapter(mActivity: Activity, productItems: List<ProductItem?>?) :
    RecyclerView.Adapter<NewArrivalAdapter.ViewHolder>() {

    var context: Activity? = null
    private var productItems: List<ProductItem?>? = null
    var binding: ItemNewArrivalCatBinding? = null

    init {
        this.context = mActivity
        this.productItems = productItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        binding = ItemNewArrivalCatBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Utils.setImageUsingGlide(context,
            "https://seller.shoponn.co.in/${productItems!![position]?.imagePath2}",
            binding!!.ivCatImage)
        binding!!.tvMrp.text = NumberFormat.getCurrencyInstance(
            Locale("en", "in")).format(
            productItems?.get(position)?.mRP)
        binding!!.tvMsp.text = NumberFormat.getCurrencyInstance(
            Locale("en", "in")).format(
            productItems?.get(position)?.mSP)
        binding!!.tvCatName.text = productItems?.get(position)?.title
        binding!!.tvDiscount.text = "${productItems?.get(position)?.discountPercent.toString()} %"

        context?.resources?.getColor(R.color.white)
            ?.let { binding!!.tvDiscount.setTextColor(it) }
        context?.resources?.getColor(R.color.colorPrimary)
            ?.let { binding!!.tvMsp.setTextColor(it) }
        binding!!.tvMrp.paintFlags = binding!!.tvMrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    override fun getItemCount(): Int {
        return productItems?.size!!
    }

    class ViewHolder(binding: ItemNewArrivalCatBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}
