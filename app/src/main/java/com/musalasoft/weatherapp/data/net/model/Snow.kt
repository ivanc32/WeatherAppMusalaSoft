package com.musalasoft.weatherapp.data.net.model

import com.google.gson.annotations.SerializedName

data class Snow(
    @SerializedName("1h")
    val lastHour: Int,

    @SerializedName("3h")
    val lastThreeHours: Int
)
