package com.shoponn.activity.signup.model


import com.google.gson.annotations.SerializedName

data class CustomerSaveResponse(
    @SerializedName("Flag")
    var flag: Int?
)