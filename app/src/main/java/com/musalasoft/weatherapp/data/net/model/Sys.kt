package com.musalasoft.weatherapp.data.net.model

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("type")
    val type: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("message")
    val message: Float,

    @SerializedName("country")
    val country: String,

    @SerializedName("sunrise")
    val sunrise: String,

    @SerializedName("sunset")
    val sunset: String
)
