package com.shoponn.activity.home.model


import com.google.gson.annotations.SerializedName

data class HomePageBannerDetailsResponse(
    @SerializedName("Error")
    var error: Boolean?,
    @SerializedName("HomePageBanner")
    var homePageBanner: List<HomePageBanner?>?,
    @SerializedName("Message")
    var message: String?
)