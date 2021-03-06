package com.musalasoft.weatherapp.data.net.model

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    val speed: Float,

    @SerializedName("deg")
    val degrees: Int,

    @SerializedName("gust")
    val gust: Float
)
