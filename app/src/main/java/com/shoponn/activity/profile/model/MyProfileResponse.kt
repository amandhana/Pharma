package com.shoponn.activity.profile.model


import com.google.gson.annotations.SerializedName

data class MyProfileResponse(
    @SerializedName("CustomerAddressResponse")
    var customerAddressResponse: List<CustomerAddressResponse?>?,
    @SerializedName("Error")
    var error: Boolean?,
    @SerializedName("Message")
    var message: String?
)