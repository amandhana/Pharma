package com.shoponn.activity.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoponn.activity.home.model.HomeCatModel
import com.shoponn.databinding.ItemHomeCatBinding

internal class HomeCatAdapter(context: Activity, superCategories: List<HomeCatModel?>?) :
    RecyclerView.Adapter<HomeCatAdapter.ViewHolder>() {
    var context: Activity? = null
    var superCategories: List<HomeCatModel?>? = null
    var binding: ItemHomeCatBinding? = null

    init {
        this.context = context
        this.superCategories = superCategories
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
        Log.e("TAG", "onBindViewHolder: " + "${superCategories?.get(position)?.catID}")
        binding!!.tvCatName.text = superCategories!!.get(position)!!.catName
        /*when (superCategories!![position]?.catID) {
            0 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_1)
                binding!!.tvCatName.text = "New Arrival"
                binding!!.ivCatImage.setOnClickListener(View.OnClickListener {
                    if (context is HomeActivity) {
                        (context as HomeActivity).resetBottom("home")
                        (context as HomeActivity).loadFragment(NewArrivalFragment::class.java.name,
                            NewArrivalFragment.newInstance()!!)
                    }
                })
            }
            1 -> {
                binding!!.tvCatName.text = "Automotive, Real Estate & Industrial"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_real_estate)
            }
            2 -> {
                binding!!.tvCatName.text = "Toys, Kids & Babies"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_toys)
            }
            3 -> {
                binding!!.tvCatName.text = "Computers, Laptops & Gaming"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_computers)
            }
            4 -> {
                binding!!.tvCatName.text = "Gifts & Sweets"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_sweets)
            }
            5 -> {
                binding!!.tvCatName.text = "Mens Zone"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_mens_zone)
            }
            6 -> {
                binding!!.tvCatName.text = "Hobbies & E-Learning"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_hobbies)
            }
            7 -> {
                binding!!.tvCatName.text = "Tv, Audio, Cameras & Appliances"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_tv_camera)
            }
            8 -> {
                binding!!.tvCatName.text = "Home & Living"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_home)
            }
            9 -> {
                binding!!.tvCatName.text = "Mobiles, Tablets & Office Equipments"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_mobile)
            }
            10 -> {
                binding!!.tvCatName.text = "Womens Zone"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_womens_zone)
            }
            11 -> {
                binding!!.tvCatName.text = "Bags & Luggage"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_bags)
            }
            12 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_jewellery)
                binding!!.tvCatName.text = "Jewellery & Gold"
            }
            13 -> {
                binding!!.tvCatName.text = "Sports & Fitness"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_sports_fitness)
            }
            14 -> {
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_beauty)
                binding!!.tvCatName.text = "Beauty, Health & Fmcg"
            }
            16 -> {
                binding!!.tvCatName.text = "GST"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_gst)
            }
            17 -> {
                binding!!.tvCatName.text = "Work From Home"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_hobbies)
            }
            18 -> {
                binding!!.tvCatName.text = "Eateries"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_eateries)
            }
            19 -> {
                binding!!.tvCatName.text = "Prescription"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_prescription)
            }
            21 -> {
                binding!!.tvCatName.text = "Non-Prescription"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_2)
            }
            22 -> {
                binding!!.tvCatName.text = "Breakfast"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_breakfast)
            }
            23 -> {
                binding!!.tvCatName.text = "Brunch"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_brunch)
            }
            24 -> {
                binding!!.tvCatName.text = "Lunch"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_lunch)
            }
            25 -> {
                binding!!.tvCatName.text = "Dinner"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_dinner)
            }
            15 -> {
                binding!!.tvCatName.text = "Books"
                binding!!.ivCatImage.setBackgroundResource(R.drawable.cat_books)
                binding!!.ivCatImage.setOnClickListener(View.OnClickListener {
                    if (context is HomeActivity) {
                        (context as HomeActivity).resetBottom("home")
                        (context as HomeActivity).loadFragment(BooksFragment::class.java.name,
                            BooksFragment.newInstance()!!)
                    }
                })
            }
        }*/
    }


    override fun getItemCount(): Int {
        return superCategories!!.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ViewHolder(binding: ItemHomeCatBinding) : RecyclerView.ViewHolder(binding.root)
}