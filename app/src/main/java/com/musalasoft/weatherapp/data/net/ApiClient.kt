package com.musalasoft.weatherapp.data.net

import com.musalasoft.weatherapp.data.net.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") appId: String,
        @Query("mode") mode: String?,
        @Query("lang") language: String?,
        @Query("units") units: String?
    ): Response<WeatherResponse>

    @GET("weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appId: String,
        @Query("mode") mode: String?,
        @Query("lang") language: String?,
        @Query("units") units: String?
    ): Response<WeatherResponse>
}