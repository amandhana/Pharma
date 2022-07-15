package com.shoponn.activity.signup.model


import com.google.gson.annotations.SerializedName

data class CustomerMaxIDResponse(
    @SerializedName("CustomerMaxRegId")
    var customerMaxRegId: Int?
)