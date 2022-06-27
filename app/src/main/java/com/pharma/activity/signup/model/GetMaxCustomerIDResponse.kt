package com.pharma.activity.signup.model


import com.google.gson.annotations.SerializedName

data class GetMaxCustomerIDResponse(
    @SerializedName("CustomerMaxIDResponse")
    var customerMaxIDResponse: List<CustomerMaxIDResponse?>?,
    @SerializedName("Error")
    var error: Boolean?,
    @SerializedName("Message")
    var message: String?
)