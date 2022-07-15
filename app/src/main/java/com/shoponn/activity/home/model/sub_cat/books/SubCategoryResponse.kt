package com.shoponn.activity.home.model.sub_cat.books


import com.google.gson.annotations.SerializedName

data class SubCategoryResponse(
    @SerializedName("CategoryID")
    var categoryID: Int?,
    @SerializedName("SubCategory")
    var subCategory: String?,
    @SerializedName("SubcategoryID")
    var subcategoryID: Int?
)