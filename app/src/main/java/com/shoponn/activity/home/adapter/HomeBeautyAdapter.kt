package com.shoponn.activity.home.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoponn.R
import com.shoponn.activity.home.model.CategoryResponse
import com.shoponn.databinding.ItemElectronicsHomeBinding

internal class HomeBeautyAdapter(context: Activity, categories: List<CategoryResponse?>?) :
    RecyclerView.Adapter<HomeBeautyAdapter.ViewHolder>() {

    var context: Activity? = null
    private var categories: List<CategoryResponse?>? = null
    var binding: ItemElectronicsHomeBinding? = null

    init {
        this.context = context
        this.categories = categories
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
        position: Int
    ) {
        binding!!.ivElectronicsImage.setBackgroundResource(R.drawable.cat_5)
        binding!!.tvElectronicsName.text = categories?.get(position)?.category
        /*when (categories?.get(position)?.category) {
            "GAMING" -> {
                binding!!.ivElectronicsImage.setBackgroundResource(R.drawable.electronics_3)
                binding!!.tvElectronicsName.text = categories?.get(position)?.category
            }
            "APPLIANCES" -> {
                binding!!.ivElectronicsImage.setBackgroundResource(R.drawable.electronics_1)
                binding!!.tvElectronicsName.text = categories?.get(position)?.category
            }
            "COMPUTERS & PERIPHERALS" -> {
                binding!!.ivElectronicsImage.setBackgroundResource(R.drawable.electronics_3)
                binding!!.tvElectronicsName.text = categories?.get(position)?.category
            }
            "TVs, AUDIO & VIDEO" -> {
                binding!!.ivElectronicsImage.setBackgroundResource(R.drawable.electronics_3)
                binding!!.tvElectronicsName.text = categories?.get(position)?.category
            }
        }*/
    }


    override fun getItemCount(): Int {
        return categories?.size!!
    }

    class ViewHolder(binding: ItemElectronicsHomeBinding) : RecyclerView.ViewHolder(binding.root)
}