package com.musalasoft.weatherapp.data.net.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceBuilder {
    private const val API_URL = "https://api.openweathermap.org/data/2.5/"
    private val okHttpClient = OkHttpClient().newBuilder().addInterceptor(getInterceptor()).build()

    fun getRetrofitBuilder(): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    private fun getInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}