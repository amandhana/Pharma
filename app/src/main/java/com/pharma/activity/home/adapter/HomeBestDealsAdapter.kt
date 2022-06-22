package com.pharma.activity.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharma.R
import com.pharma.databinding.ItemHomeBestDealsBinding

internal class HomeBestDealsAdapter(context: Activity) :
    RecyclerView.Adapter<HomeBestDealsAdapter.ViewHolder>() {
    var context: Activity? = null
    var binding: ItemHomeBestDealsBinding? = null

    init {
        this.context = context
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
        when (position) {
            0 -> {
                binding!!.ivBestDealImage.setBackgroundResource(R.drawable.best_deal_1)
                binding!!.tvBestDealName.text = "New Arrival"
            }
            1 -> {
                binding!!.ivBestDealImage.setBackgroundResource(R.drawable.best_deal_2)
                binding!!.tvBestDealName.text = "Pharma"
            }
            2 -> {
                binding!!.ivBestDealImage.setBackgroundResource(R.drawable.best_deal_3)
                binding!!.tvBestDealName.text = "FMCG"
            }
            3 -> {
                binding!!.ivBestDealImage.setBackgroundResource(R.drawable.best_deal_4)
                binding!!.tvBestDealName.text = "Electronics"
            }
            4 -> {
                binding!!.ivBestDealImage.setBackgroundResource(R.drawable.best_deal_1)
                binding!!.tvBestDealName.text = "Fashion"
            }
            5 -> {
                binding!!.ivBestDealImage.setBackgroundResource(R.drawable.best_deal_3)
                binding!!.tvBestDealName.text = "Music"
            }
        }
    }


    override fun getItemCount(): Int {
        return 6
    }

    class ViewHolder(binding: ItemHomeBestDealsBinding) : RecyclerView.ViewHolder(binding.root)
}