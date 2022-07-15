package com.shoponn.activity.home.model


import com.google.gson.annotations.SerializedName

data class ProductItem(
    @SerializedName("DiscountPercent")
    var discountPercent: Int?,
    @SerializedName("Id")
    var id: Int?,
    @SerializedName("ImagePath2")
    var imagePath2: String?,
    @SerializedName("MRP")
    var mRP: Int?,
    @SerializedName("MSP")
    var mSP: Int?,
    @SerializedName("SubCatID")
    var subCatID: Int?,
    @SerializedName("SubCatName")
    var subCatName: Any?,
    @SerializedName("Title")
    var title: String?
)