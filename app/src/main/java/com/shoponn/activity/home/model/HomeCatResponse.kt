package com.shoponn.activity.home.model

import com.google.gson.annotations.SerializedName

data class HomeCatResponse(
    @SerializedName("Message")
    var message: String,
    @SerializedName("superCategories")
    var superCategories: List<HomeCatModel?>?
)
