package com.shoponn.activity.home.books.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoponn.activity.home.model.sub_cat.books.SubCategoryResponse
import com.shoponn.databinding.ItemBooksSubCatBinding
import java.util.*

internal class BooksSubCatAdapter(mActivity: Activity, subCategoryResponse: List<SubCategoryResponse?>?) :
    RecyclerView.Adapter<BooksSubCatAdapter.ViewHolder>() {

    private val TAG = "BooksSubCatAdapter"
    var context: Activity? = null
    private var subCategoryResponse: List<SubCategoryResponse?>? = null
    var binding: ItemBooksSubCatBinding? = null

    init {
        this.context = mActivity
        this.subCategoryResponse = subCategoryResponse
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemBooksSubCatBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding!!.tvBooksSubTitle.text = subCategoryResponse!![position]?.subCategory
        binding!!.tvBooksSubTitle.setOnClickListener {
            Log.e(TAG, "onBindViewHolder: ${subCategoryResponse!![position]?.categoryID}")
            /*(context as HomeActivity).loadFragment(BooksListFragment::class.java.name, BooksListFragment.newInstance()!!)*/
        }
    }

    override fun getItemCount(): Int {
        return subCategoryResponse?.size!!
    }

    inner class ViewHolder(binding: ItemBooksSubCatBinding) : RecyclerView.ViewHolder(binding.root)

}
