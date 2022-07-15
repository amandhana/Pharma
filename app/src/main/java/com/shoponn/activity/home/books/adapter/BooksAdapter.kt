package com.shoponn.activity.home.books.adapter

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.RequestParams
import com.shoponn.R
import com.shoponn.activity.home.activity.HomeActivity
import com.shoponn.activity.home.books.fragment.BooksListFragment
import com.shoponn.activity.home.books.model.CategoryResponse
import com.shoponn.activity.home.model.sub_cat.books.BooksSubCatResponse
import com.shoponn.api.Communicator
import com.shoponn.api.Constants
import com.shoponn.api.CustomResponseListener
import com.shoponn.databinding.ItemBooksCatBinding
import com.shoponn.support.Utils

internal class BooksAdapter(mActivity: Activity, categoryResponse: List<CategoryResponse?>?) :
    RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    private val TAG = "BooksAdapter"
    var context: Activity? = null
    private var categoryResponse: List<CategoryResponse?>? = null
    var binding: ItemBooksCatBinding? = null

    private lateinit var progressDialog: ProgressDialog
    private val viewPool = RecyclerView.RecycledViewPool()

    init {
        this.context = mActivity
        this.categoryResponse = categoryResponse
        this.progressDialog = ProgressDialog(this.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        binding = ItemBooksCatBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding!!.tvBooksTitle.text = categoryResponse!![position]?.category
        /*binding!!.tvBooksTitle.setOnClickListener(View.OnClickListener { view ->
            Log.e("TAG", "onBindViewHolder: ${categoryResponse!![position]?.categoryID}")
            (context as HomeActivity).loadFragment(BooksFragment::class.java.name, BooksFragment.newInstance()!!)
        })*/
        showBooksSubCatData(categoryResponse!![position]?.categoryID)
    }

    override fun getItemCount(): Int {
        return categoryResponse?.size!!
    }

    private fun showBooksSubCatData(categoryID: Int?) {
        Log.e(TAG, "showBooksSubCatData: $categoryID")
        if (context?.let { Utils.isNetworkAvailable(it) } == true) {
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val params = RequestParams()
            try {
                params.put("catID", categoryID!!)
            } catch (e: Exception) {
                Log.e(TAG, "showBooksSubCatData: $e")
            }
            val communicator = Communicator()
            communicator.get(101, context!!, Constants.Apis.GETSUBCATBOOKS, params,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        Log.e(TAG, "onResponse: $response")
                        progressDialog.dismiss()
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: BooksSubCatResponse = Utils.getObject(
                                response, BooksSubCatResponse::class.java)
                                    as BooksSubCatResponse
                            if (!modelResponse.error!!) {
                                if (modelResponse.subCategoryResponse != null &&
                                    modelResponse.subCategoryResponse!!.isNotEmpty()) {
                                    binding!!.viewSubCatLay.removeAllViews()
                                    for (position in 0 until modelResponse.subCategoryResponse!!.size) {
                                        val inflater =
                                            context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                                        val itemView: View = inflater!!.inflate(
                                            R.layout.item_books_sub_cat,
                                            null,
                                            true
                                        )

                                        val tvCatName = itemView.findViewById<TextView>(R.id.tv_books_sub_title)
                                        tvCatName.text = modelResponse.subCategoryResponse!![position]!!.subCategory

                                        tvCatName.tag = 1000 + position

                                        itemView.setOnClickListener {
                                            val tag = tvCatName.tag as Int - 1000
                                            Log.e(TAG, "onResponse: $tag, ${modelResponse.
                                            subCategoryResponse!![position]?.subcategoryID}")
                                            if (context is HomeActivity) {
                                                val bundle = Bundle().apply {
                                                    putInt(
                                                        "subCatID", modelResponse
                                                            .subCategoryResponse!![position]!!
                                                            .subcategoryID!!
                                                    ) //string and value.
                                                }
                                                (context as HomeActivity).loadFragment(
                                                    BooksListFragment::class.java.name,
                                                    BooksListFragment.newInstance()!!,
                                                    bundle
                                                )
                                            }
                                        }

                                        binding!!.viewSubCatLay.addView(itemView)
                                    }
                                    /*val childLayoutManager = LinearLayoutManager(
                                        binding!!.rvSubCat.context,
                                        RecyclerView.HORIZONTAL,
                                        false)*/
                                    /*childLayoutManager.initialPrefetchItemCount = 4
                                        binding!!.rvSubCat.apply {
                                            layoutManager = childLayoutManager
                                            adapter = BooksSubCatAdapter(context!!, modelResponse.subCategoryResponse)
                                            setRecycledViewPool(viewPool)
                                        }*/
                                    // binding!!.rvSubCat.layoutManager = childLayoutManager
                                        /*LinearLayoutManager(
                                            context,
                                            RecyclerView.VERTICAL,
                                            false
                                        )*/
                                    /*binding!!.rvSubCat.adapter =
                                        BooksSubCatAdapter(context!!, modelResponse.subCategoryResponse)*/
                                }
                            }
                        }
                    }

                    override fun onFailure(statusCode: Int, error: Throwable?) {
                        progressDialog.dismiss()
                        Log.e(TAG, "onFailure: $error")
                    }
                })
        } else {
            context?.let {
                Utils.showToastPopup(
                    it,
                    context?.resources!!.getString(R.string.internet_error)
                )
            }
        }
    }

    class ViewHolder(binding: ItemBooksCatBinding) : RecyclerView.ViewHolder(binding.root)

}
