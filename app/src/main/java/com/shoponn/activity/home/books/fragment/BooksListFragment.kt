package com.shoponn.activity.home.books.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.RequestParams
import com.shoponn.R
import com.shoponn.activity.home.activity.HomeActivity
import com.shoponn.activity.home.books.adapter.BooksAdapter
import com.shoponn.activity.home.books.model.BooksResponse
import com.shoponn.activity.home.fragment.HomeFragment
import com.shoponn.api.Communicator
import com.shoponn.api.Constants
import com.shoponn.api.CustomResponseListener
import com.shoponn.databinding.FragmentBooksListBinding
import com.shoponn.support.Utils

class BooksListFragment : Fragment(), View.OnClickListener {

    private val TAG: String = "BooksFragment"
    var binding: FragmentBooksListBinding? = null
    var mActivity: Activity? = null
    private var rootView: View? = null
    private var subCatID: Int = 0

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(TAG, "onCreateViewBooksListID: ${requireArguments().getInt("subCatID")}")
        subCatID = requireArguments().getInt("subCatID")
        // Inflate the layout for this fragment
        binding = FragmentBooksListBinding.inflate(inflater)
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
        var fragment: BooksListFragment? = null

        @JvmStatic
        fun newInstance(): BooksListFragment? {
            fragment = BooksListFragment()
            return fragment
        }

        fun getInstance(): HomeFragment? {
            return if (HomeFragment.fragment == null) HomeFragment() else HomeFragment.fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
            val params = RequestParams()
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
                                    binding!!.tvNoData.visibility = View.GONE
                                    binding!!.rvBooks.visibility = View.VISIBLE
                                    binding!!.rvBooks.layoutManager =
                                        LinearLayoutManager(
                                            mActivity,
                                            RecyclerView.VERTICAL,
                                            false
                                        )
                                    binding!!.rvBooks.adapter =
                                        BooksAdapter(mActivity!!, modelResponse.categoryResponse)
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

    private fun setUpClickListener() {

    }

    override fun onClick(view: View?) {

    }
}