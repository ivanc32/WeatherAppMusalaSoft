package com.musalasoft.weatherapp.data.net.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.musalasoft.weatherapp.data.net.ApiClient
import com.musalasoft.weatherapp.data.net.Result
import com.musalasoft.weatherapp.data.net.response.ErrorResponse
import com.musalasoft.weatherapp.data.net.response.WeatherResponse
import retrofit2.Response

object ApiService {
    private const val API_KEY = "af4359a45e4c5208e5b9d23397646671"
    private lateinit var apiClient: ApiClient

    init {
        makeService()
    }

    private fun makeService() {
        apiClient = ApiServiceBuilder.getRetrofitBuilder().create(ApiClient::class.java)
    }

    private suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Result<T, ErrorResponse> {
        return try {
            val myResp = call.invoke()
            if (myResp.isSuccessful) {
                Result.Success<T, Nothing>(myResp.body()!!)
            } else {
                val gson = Gson()
                val type = object : TypeToken<ErrorResponse>() {}.type
                val errorResponse: ErrorResponse? =
                    gson.fromJson(myResp.errorBody()!!.charStream(), type)

                Result.Error<Nothing, ErrorResponse>(errorResponse)
            }

        } catch (e: Exception) {
            Result.Exception(e.message ?: "Internet error runs")
        }
    }

    suspend fun getWeatherByCity(
        city: String,
        mode: String? = null,
        language: String? = null,
        units: String? = "metric"
    ): Result<WeatherResponse, ErrorResponse> =
        safeApiCall {
            apiClient.getWeatherByCity(
                city = city,
                appId = API_KEY,
                mode = mode,
                language = language,
                units = units
            )
        }

    suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double,
        mode: String? = null,
        language: String? = null,
        units: String? = "metric"
    ): Result<WeatherResponse, ErrorResponse> =
        safeApiCall {
            apiClient.getWeatherByCoordinates(
                latitude = latitude,
                longitude = longitude,
                appId = API_KEY,
                mode = mode,
                language = language,
                units = units
            )
        }
}