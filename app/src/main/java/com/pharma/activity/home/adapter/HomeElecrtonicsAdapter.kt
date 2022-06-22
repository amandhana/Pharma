package com.pharma.activity.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharma.R
import com.pharma.databinding.ItemElectronicsHomeBinding
import com.pharma.databinding.ItemFmcgHomeBinding

internal class HomeElecrtonicsAdapter(context: Activity) :
    RecyclerView.Adapter<HomeElecrtonicsAdapter.ViewHolder>() {
    var context: Activity? = null
    var binding: ItemElectronicsHomeBinding? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemElectronicsHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }


    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        when (position) {
            0 -> {
                binding!!.ivElectronicsImage.setBackgroundResource(R.drawable.electronics_1)
                binding!!.tvElectronicsName.text = "Bakery"
            }
            1 -> {
                binding!!.ivElectronicsImage.setBackgroundResource(R.drawable.electronics_3)
                binding!!.tvElectronicsName.text = "Beverages"
            }
            2 -> {
                binding!!.ivElectronicsImage.setBackgroundResource(R.drawable.electronics_1)
                binding!!.tvElectronicsName.text = "Cooking Oil"
            }
            3 -> {
                binding!!.ivElectronicsImage.setBackgroundResource(R.drawable.electronics_3)
                binding!!.tvElectronicsName.text = "Electronics"
            }
            4 -> {
                binding!!.ivElectronicsImage.setBackgroundResource(R.drawable.electronics_1)
                binding!!.tvElectronicsName.text = "Fashion"
            }
            5 -> {
                binding!!.ivElectronicsImage.setBackgroundResource(R.drawable.electronics_3)
                binding!!.tvElectronicsName.text = "Music"
            }
        }
    }


    override fun getItemCount(): Int {
        return 6
    }

    class ViewHolder(binding: ItemElectronicsHomeBinding) : RecyclerView.ViewHolder(binding.root)
}