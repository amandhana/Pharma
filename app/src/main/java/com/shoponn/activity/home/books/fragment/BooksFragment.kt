package com.shoponn.activity.home.books.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loopj.android.http.RequestParams
import com.shoponn.R
import com.shoponn.activity.home.activity.HomeActivity
import com.shoponn.activity.home.books.model.BooksResponse
import com.shoponn.activity.home.books.model.CategoryResponse
import com.shoponn.activity.home.books.model.CustomModel
import com.shoponn.activity.home.fragment.HomeFragment
import com.shoponn.activity.home.model.sub_cat.books.BooksSubCatResponse
import com.shoponn.api.Communicator
import com.shoponn.api.Constants
import com.shoponn.api.CustomResponseListener
import com.shoponn.databinding.FragmentBooksBinding
import com.shoponn.support.Utils

class BooksFragment : Fragment(), View.OnClickListener {

    private val TAG: String = "BooksFragment"
    var binding: FragmentBooksBinding? = null
    var mActivity: Activity? = null
    private var rootView: View? = null

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBooksBinding.inflate(inflater)
        rootView = binding!!.root
        init()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (mActivity as HomeActivity).resetBottom("home")
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: BooksFragment? = null

        @JvmStatic
        fun newInstance(): BooksFragment? {
            fragment = BooksFragment()
            return fragment
        }

        fun getInstance(): HomeFragment? {
            return if (HomeFragment.fragment == null) HomeFragment() else HomeFragment.fragment
        }
    }

    fun init() {
        mActivity = activity
        progressDialog = ProgressDialog(mActivity)
        setUpClickListener()
        showBooks()
    }

    private fun showBooks() {
        if (mActivity?.let { Utils.isNetworkAvailable(it) } == true) {
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val communicator = Communicator()
            communicator.get(101, mActivity!!, Constants.Apis.GETCATEGORYOFBOOKS,
                object : CustomResponseListener {
                    override fun onResponse(requestCode: Int, response: String?) {
                        progressDialog.dismiss()
                        if (response != null && response.isNotEmpty()) {
                            val modelResponse: BooksResponse =
                                Utils.getObject(response, BooksResponse::class.java)
                                        as BooksResponse
                            if (!modelResponse.error!!) {
                                if (modelResponse.categoryResponse != null &&
                                    modelResponse.categoryResponse?.size!! > 0) {
                                    getSubCats(modelResponse.categoryResponse)


                                    /*binding!!.tvNoData.visibility = View.GONE
                                    binding!!.rvBooks.visibility = View.VISIBLE
                                    binding!!.rvBooks.layoutManager =
                                        LinearLayoutManager(
                                            mActivity,
                                            RecyclerView.VERTICAL,
                                            false
                                        )
                                    binding!!.rvBooks.adapter =
                                        BooksAdapter(mActivity!!, modelResponse.categoryResponse)*/
                                } else {
                                    binding!!.tvNoData.visibility = View.VISIBLE
                                    binding!!.rvBooks.visibility = View.GONE
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
            mActivity?.let {
                Utils.showToastPopup(
                    it,
                    resources.getString(R.string.internet_error)
                )
            }
        }
    }

    private fun getSubCats(categoryResponse: List<CategoryResponse?>?){
        val listMain : List<CustomModel> = ArrayList()
        val customModel  = CustomModel()

        Log.e("list","start")
        var position = 0
        while (position < categoryResponse!!.size){
            val catId = categoryResponse.get(position)!!.categoryID
            customModel.setMainCat(catId.toString())
            if (Utils.isNetworkAvailable(mActivity!!)){
                val params = RequestParams()
                try {
                    params.put("catID", catId!!)
                } catch (e: Exception) {
                    Log.e(TAG, "showBooksSubCatData: $e")
                }
                val communicator = Communicator()
                communicator.get(101, mActivity!!, Constants.Apis.GETSUBCATBOOKS, params,
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
                                        customModel.setSubCatList(modelResponse.subCategoryResponse!!)
                                        (listMain as ArrayList<CustomModel>).add(customModel)
                                        ++position
                                    }
                                }
                            }
                        }

                        override fun onFailure(statusCode: Int, error: Throwable?) {
                            progressDialog.dismiss()
                            Log.e(TAG, "onFailure: $error")
                        }
                    })
            }
        }
        Log.e("list",listMain.size.toString())
    }

    private fun setUpClickListener() {

    }

    override fun onClick(view: View?) {

    }
}