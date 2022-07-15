package com.shoponn.api

interface CustomResponseListener {
    fun onResponse(requestCode: Int, response: String?)
    fun onFailure(statusCode: Int, error: Throwable?)
}