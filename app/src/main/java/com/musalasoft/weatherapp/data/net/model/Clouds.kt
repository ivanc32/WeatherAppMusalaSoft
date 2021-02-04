package com.musalasoft.weatherapp.data.net.model

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val percentage: Int
)
