package com.shoponn.activity.home.model


import com.google.gson.annotations.SerializedName

data class BestDealsResponse(
    @SerializedName("Error")
    var error: Boolean?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("ProductItems")
    var productItems: List<ProductItem?>?
)