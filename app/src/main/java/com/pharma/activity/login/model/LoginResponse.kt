package com.pharma.activity.login.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("CustomerAuthorizedResponse")
    var customerAuthorizedResponse: List<CustomerAuthorizedResponse?>?,
    @SerializedName("Error")
    var error: Boolean?,
    @SerializedName("Message")
    var message: String?
)