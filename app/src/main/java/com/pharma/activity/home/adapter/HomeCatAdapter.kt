package com.pharma.activity.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharma.R
import com.pharma.databinding.ItemHomeCatBinding

internal class HomeCatAdapter(context: Activity) :
    RecyclerView.Adapter<HomeCatAdapter.ViewHolder>() {
    var context: Activity? = null
    var binding: ItemHomeCatBinding? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemHomeCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }


    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        when (position) {
            0 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_1)
                binding!!.tvCatName.text = "New Arrival"
            }
            1 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_2)
                binding!!.tvCatName.text = "Pharma"
            }
            2 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_3)
                binding!!.tvCatName.text = "FMCG"
            }
            3 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_4)
                binding!!.tvCatName.text = "Electronics"
            }
            4 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_5)
                binding!!.tvCatName.text = "Fashion"
            }
            5 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_6)
                binding!!.tvCatName.text = "Music"
            }
        }
    }


    override fun getItemCount(): Int {
        return 6
    }

    class ViewHolder(binding: ItemHomeCatBinding) : RecyclerView.ViewHolder(binding.root)
}