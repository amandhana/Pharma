package com.pharma.activity.profile.model


import com.google.gson.annotations.SerializedName

data class CustomerAddressResponse(
    @SerializedName("AddressLine1")
    var addressLine1: String?,
    @SerializedName("AddressLine2")
    var addressLine2: String?,
    @SerializedName("City")
    var city: String?,
    @SerializedName("CountryID")
    var countryID: Int?,
    @SerializedName("CountryName")
    var countryName: String?,
    @SerializedName("CustomerID")
    var customerID: Int?,
    @SerializedName("DateOfBirth")
    var dateOfBirth: String?,
    @SerializedName("EmailId")
    var emailId: String?,
    @SerializedName("FirstName")
    var firstName: String?,
    @SerializedName("GSTNo")
    var gSTNo: String?,
    @SerializedName("Gender")
    var gender: String?,
    @SerializedName("Landmark")
    var landmark: String?,
    @SerializedName("LastName")
    var lastName: String?,
    @SerializedName("Latitude")
    var latitude: Int?,
    @SerializedName("Longitude")
    var longitude: Int?,
    @SerializedName("MobileNo")
    var mobileNo: String?,
    @SerializedName("OtherMobileNo")
    var otherMobileNo: String?,
    @SerializedName("Password")
    var password: String?,
    @SerializedName("PhoneNo")
    var phoneNo: String?,
    @SerializedName("Pincode")
    var pincode: String?,
    @SerializedName("State")
    var state: String?,
    @SerializedName("StateID")
    var stateID: Int?,
    @SerializedName("Title")
    var title: String?,
    @SerializedName("TitleID")
    var titleID: Int?
)