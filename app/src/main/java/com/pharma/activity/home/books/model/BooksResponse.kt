package com.pharma.activity.home.books.model


import com.google.gson.annotations.SerializedName

data class BooksResponse(
    @SerializedName("CategoryResponse")
    var categoryResponse: List<CategoryResponse?>?,
    @SerializedName("Error")
    var error: Boolean?,
    @SerializedName("Message")
    var message: String?
)