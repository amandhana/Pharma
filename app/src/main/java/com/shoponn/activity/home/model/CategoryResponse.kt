package com.shoponn.activity.home.model


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("Category")
    var category: String?,
    @SerializedName("CategoryID")
    var categoryID: Int?,
    @SerializedName("SuperID")
    var superID: Int?
)