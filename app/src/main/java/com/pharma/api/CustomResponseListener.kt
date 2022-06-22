package com.pharma.api

interface CustomResponseListener {
    fun onResponse(requestCode: Int, response: String?)
    fun onFailure(statusCode: Int, error: Throwable?)
}