package com.shoponn.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    companion object{
        fun getRetroFitClient(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(240, TimeUnit.SECONDS)
            httpClient.connectTimeout(240, TimeUnit.SECONDS)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
            val client = httpClient.build()
            return Retrofit.Builder().baseUrl(Constants.BASE_URL_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
        }

    }
}