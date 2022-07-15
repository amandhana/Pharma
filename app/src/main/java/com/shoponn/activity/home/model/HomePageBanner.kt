package com.shoponn.activity.home.model


import com.google.gson.annotations.SerializedName

data class HomePageBanner(
    @SerializedName("AlternateText")
    var alternateText: String?,
    @SerializedName("BannerID")
    var bannerID: Int?,
    @SerializedName("ImgPath")
    var imgPath: String?,
    @SerializedName("Url")
    var url: String?
)