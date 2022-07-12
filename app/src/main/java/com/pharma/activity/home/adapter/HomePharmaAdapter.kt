package com.pharma.activity.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharma.R
import com.pharma.databinding.ItemPharmaHomeBinding

internal class HomePharmaAdapter(context: Activity) :
    RecyclerView.Adapter<HomePharmaAdapter.ViewHolder>() {
    var context: Activity? = null
    var binding: ItemPharmaHomeBinding? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemPharmaHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }


    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        when (position) {
            0 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.pharma_1)
                binding!!.tvPharmaName.text = "Regular Checkup"
            }
            1 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.pharma_2)
                binding!!.tvPharmaName.text = "Medical Report"
            }
            2 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.pharma_3)
                binding!!.tvPharmaName.text = "Medicines"
            }
        }
    }


    override fun getItemCount(): Int {
        return 3
    }

    class ViewHolder(binding: ItemPharmaHomeBinding) : RecyclerView.ViewHolder(binding.root)
}