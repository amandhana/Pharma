package com.agot.api

import android.app.Activity
import com.agot.support.Utils
import com.pharma.api.CustomResponseListener
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity

class Communicator {
    fun postLogin(reqCode : Int,context : Activity,url : String,params : RequestParams,responseListener : CustomResponseListener){
        val client = AsyncHttpClient()
        client.setTimeout(5 * 60000)
        client.responseTimeout = 5 * 60000
        client.post(context,url,params,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val response = String(responseBody!!)
                Utils.printLog("Response",response)
                try {
                    responseListener.onResponse(reqCode,response.trim())
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Utils.printLog("Error",error.toString())
                Utils.hideProgressBar()
            }

        })
    }

    fun post(reqCode : Int,context : Activity,url : String,params : RequestParams,responseListener : CustomResponseListener){
        val client = AsyncHttpClient()
        client.setTimeout(5 * 60000)
        client.responseTimeout = 5 * 60000
        client.post(context,url,params,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val response = String(responseBody!!)
                Utils.printLog("Response",response)
                try {
                    responseListener.onResponse(reqCode,response.trim())
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Utils.printLog("Error",error.toString())
                Utils.hideProgressBar()
                try {
                    responseListener.onFailure(reqCode,error)
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

        })
    }

    fun postWithoutHeader(reqCode : Int,context : Activity,url : String,params : RequestParams,responseListener : CustomResponseListener){
        val client = AsyncHttpClient()
        client.setTimeout(5 * 60000)
        client.responseTimeout = 5 * 60000
        client.post(context,url,params,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val response = String(responseBody!!)
                Utils.printLog("Response",response)
                try {
                    responseListener.onResponse(reqCode,response.trim())
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Utils.printLog("Error",error.toString())
                Utils.hideProgressBar()
            }

        })
    }

    fun postMultipart(reqCode : Int,context : Activity,url : String,params : RequestParams,responseListener : CustomResponseListener){
        val client = AsyncHttpClient()
        client.addHeader("Content-Type", "multipart/form-data")
        client.setTimeout(5 * 60000)
        client.responseTimeout = 5 * 60000
        client.post(context,url,params,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val response = String(responseBody!!)
                Utils.printLog("Response",response)
                try {
                    responseListener.onResponse(reqCode,response.trim())
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Utils.printLog("Error",error.toString())
                Utils.hideProgressBar()
            }

        })
    }

    fun get(reqCode : Int,context : Activity,url : String,responseListener : CustomResponseListener){
        val client = AsyncHttpClient()
        client.setTimeout(5 * 60000)
        client.responseTimeout = 5 * 60000
        client.get(context,url,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val response = String(responseBody!!)
                Utils.printLog("Response",response)
                try {
                    responseListener.onResponse(reqCode,response.trim())
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Utils.printLog("Error",error.toString())
                Utils.hideProgressBar()
            }

        })
    }

    fun get(reqCode : Int,context : Activity,url : String,params: RequestParams,responseListener : CustomResponseListener){
        val client = AsyncHttpClient()
        client.setTimeout(5 * 60000)
        client.responseTimeout = 5 * 60000
        client.get(context,url,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val response = String(responseBody!!)
                Utils.printLog("Response",response)
                try {
                    responseListener.onResponse(reqCode,response.trim())
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Utils.printLog("Error",error.toString())
                Utils.hideProgressBar()
            }

        })
    }

    fun post(reqCode : Int,context : Activity,url : String,entity: StringEntity,responseListener : CustomResponseListener){
        val client = AsyncHttpClient()
        client.setTimeout(5 * 60000)
        client.responseTimeout = 5 * 60000
        client.post(context,url,entity,"application/json",object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val response = String(responseBody!!)
                Utils.printLog("Response",response)
                try {
                    responseListener.onResponse(reqCode,response.trim())
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Utils.printLog("Error",error.toString())
                Utils.hideProgressBar()
            }

        })
    }

}