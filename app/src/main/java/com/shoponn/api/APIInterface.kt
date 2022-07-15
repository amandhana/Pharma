package com.shoponn.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface APIInterface {
    @POST("project_communications")
    fun uploadProjectCommunication(@Body file: RequestBody?): Call<ResponseBody?>?

    @POST("add_projects")
    fun addProject(@Body file: RequestBody?): Call<ResponseBody?>?
}