package com.shoponn.activity.home.newarrival.model


import com.google.gson.annotations.SerializedName

data class NewArrivalResponse(
    @SerializedName("Error")
    var error: Boolean?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("ProductItems")
    var productItems: List<ProductItem?>?
)