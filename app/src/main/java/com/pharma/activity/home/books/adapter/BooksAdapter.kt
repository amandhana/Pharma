package com.pharma.activity.home.books.adapter

import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharma.R
import com.pharma.activity.home.books.model.CategoryResponse
import com.pharma.activity.home.newarrival.model.ProductItem
import com.pharma.databinding.ItemNewArrivalCatBinding
import com.pharma.support.Utils
import java.text.NumberFormat
import java.util.*

internal class BooksAdapter(mActivity: Activity, categoryResponse: List<CategoryResponse?>?) :
    RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    var context: Activity? = null
    private var categoryResponse: List<CategoryResponse?>? = null
    var binding: ItemNewArrivalCatBinding? = null

    init {
        this.context = mActivity
        this.categoryResponse = categoryResponse
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        binding = ItemNewArrivalCatBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return categoryResponse?.size!!
    }

    class ViewHolder(binding: ItemNewArrivalCatBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}
