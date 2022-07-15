package com.shoponn.activity.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoponn.R
import com.shoponn.databinding.ItemFmcgHomeBinding

internal class HomeFmcgAdapter(context: Activity) :
    RecyclerView.Adapter<HomeFmcgAdapter.ViewHolder>() {
    var context: Activity? = null
    var binding: ItemFmcgHomeBinding? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemFmcgHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }


    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        when (position) {
            0 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.fmcg_1)
                binding!!.tvFmcgName.text = "Bakery"
            }
            1 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.fmcg_2)
                binding!!.tvFmcgName.text = "Beverages"
            }
            2 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.fmcg_3)
                binding!!.tvFmcgName.text = "Cooking Oil"
            }
            3 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.fmcg_1)
                binding!!.tvFmcgName.text = "Electronics"
            }
            4 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.fmcg_2)
                binding!!.tvFmcgName.text = "Fashion"
            }
            5 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.fmcg_3)
                binding!!.tvFmcgName.text = "Music"
            }
        }
    }


    override fun getItemCount(): Int {
        return 6
    }

    class ViewHolder(binding: ItemFmcgHomeBinding) : RecyclerView.ViewHolder(binding.root)
}