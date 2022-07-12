package com.pharma.activity.home.model

import com.google.gson.annotations.SerializedName

data class HomeCatModel(
    @SerializedName("SuperID")
    var catID: Int?,
    @SerializedName("SuperCategory")
    var catName: String?,
    @SerializedName("SuperCatImage")
    var catImage: Int
)
