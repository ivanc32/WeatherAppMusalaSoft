package com.musalasoft.weatherapp.data.model

data class WeatherDto(
    val conditions: String,
    val icon: String,
    val temperature: Int,
    val temperatureFeelsLike: Int,
    val temperatureMin: Int,
    val temperatureMax: Int,
    val humidity: Int,
    val windSpeed: Float,
    val cityName: String
)