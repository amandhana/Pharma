package com.shoponn.activity.home.model.sub_cat.books


import com.google.gson.annotations.SerializedName

data class BooksSubCatResponse(
    @SerializedName("Error")
    var error: Boolean?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("SubCategoryResponse")
    var subCategoryResponse: List<SubCategoryResponse?>?
)