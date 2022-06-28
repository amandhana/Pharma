package com.pharma.activity.signup.model


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("CustomerSaveResponse")
    var customerSaveResponse: List<CustomerSaveResponse?>?,
    @SerializedName("Error")
    var error: Boolean?,
    @SerializedName("Message")
    var message: String?
)