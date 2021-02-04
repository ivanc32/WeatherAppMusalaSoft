package com.musalasoft.weatherapp.data.net.model

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    val temperature: Float,

    @SerializedName("feels_like")
    val feelsLike: Float,

    @SerializedName("temp_min")
    val temperatureMinimum: Float,

    @SerializedName("temp_max")
    val temperatureMaximum: Float,

    @SerializedName("pressure")
    val pressure: Int,

    @SerializedName("sea_level")
    val pressureSeaLevel: Int,

    @SerializedName("grnd_level")
    val pressureGroundLevel: Int,

    @SerializedName("humidity")
    val humidity: Int
)
