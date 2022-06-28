package com.pharma.activity.home.model


import com.google.gson.annotations.SerializedName

data class ElectronicsCategoryResponse(
    @SerializedName("CategoryResponse")
    var categoryResponse: List<CategoryResponse?>?,
    @SerializedName("Error")
    var error: Boolean?,
    @SerializedName("Message")
    var message: String?
)