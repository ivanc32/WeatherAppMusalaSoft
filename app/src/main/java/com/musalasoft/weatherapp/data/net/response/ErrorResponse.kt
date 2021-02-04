package com.musalasoft.weatherapp.data.net.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("cod")
    val statusCode: String,

    @SerializedName("message")
    val message: String
)