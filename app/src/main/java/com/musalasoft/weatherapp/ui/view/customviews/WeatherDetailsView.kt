package com.musalasoft.weatherapp.ui.view.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.musalasoft.weatherapp.R
import kotlinx.android.synthetic.main.weather_details_view.view.*

class WeatherDetailsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.weather_details_view, this)
    }

    fun setCityName(cityName: String?) {
        tv_weatherDetails_cityName.text = cityName
    }

    fun setConditionsIcon(icon: String?) {
        Glide.with(context)
            .load(String.format("https://openweathermap.org/img/wn/%s@2x.png", icon))
            .into(iv_weatherDetails_conditionsIcon)
    }

    fun setTemperature(temperature: Int?) {
        tv_weatherDetails_temperature.text = String.format("%d°C", temperature)
    }

    fun setTemperatureDetails(temperatureDetails: String?) {
        tv_weatherDetails_temperatureDetails.text = temperatureDetails
    }

    fun setConditions(conditions: String?) {
        tv_weatherDetails_conditions.text = conditions
    }
}