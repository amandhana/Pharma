package com.pharma.activity.login.model


import com.google.gson.annotations.SerializedName

data class CustomerAuthorizedResponse (
    @SerializedName("CustomerRegId")
    var customerRegId: Long?,
    @SerializedName("EmailId")
    var emailId: String?,
    @SerializedName("FirstName")
    var firstName: String?,
    @SerializedName("LastName")
    var lastName: String?,
    @SerializedName("Password")
    var password: String?
)