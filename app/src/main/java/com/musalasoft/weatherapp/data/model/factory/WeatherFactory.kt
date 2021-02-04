package com.musalasoft.weatherapp.data.model.factory

import com.musalasoft.weatherapp.data.model.WeatherDto
import com.musalasoft.weatherapp.data.net.response.WeatherResponse

object WeatherFactory {
    fun fromResponseToDto(weatherResponse: WeatherResponse): WeatherDto =
        WeatherDto(
            conditions = weatherResponse.weather[0].main,
            icon = weatherResponse.weather[0].icon,
            temperature = weatherResponse.main.temperature.toInt(),
            temperatureFeelsLike = weatherResponse.main.feelsLike.toInt(),
            temperatureMax = weatherResponse.main.temperatureMaximum.toInt(),
            temperatureMin = weatherResponse.main.temperatureMinimum.toInt(),
            humidity = weatherResponse.main.humidity,
            windSpeed = weatherResponse.wind.speed,
            cityName = weatherResponse.cityName
        )
}