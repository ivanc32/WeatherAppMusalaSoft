package com.musalasoft.weatherapp.data.net.response

import com.google.gson.annotations.SerializedName
import com.musalasoft.weatherapp.data.net.model.*

data class WeatherResponse(
    @SerializedName("coord")
    val coordinates: Coordinates,

    @SerializedName("weather")
    val weather: List<Weather>,

    @SerializedName("base")
    val base: String,

    @SerializedName("main")
    val main: Main,

    @SerializedName("wind")
    val wind: Wind,

    @SerializedName("clouds")
    val clouds: Clouds,

    @SerializedName("rain")
    val rain: Rain,

    @SerializedName("snow")
    val snow: Snow,

    @SerializedName("date")
    val date: String,

    @SerializedName("sys")
    val sys: Sys,

    @SerializedName("timezone")
    val timezone: Int,

    @SerializedName("id")
    val cityId: Int,

    @SerializedName("name")
    val cityName: String,

    @SerializedName("cod")
    val cod: Int
)
