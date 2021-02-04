package com.musalasoft.weatherapp.ui.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musalasoft.weatherapp.data.model.ErrorType
import com.musalasoft.weatherapp.data.model.WeatherDto
import com.musalasoft.weatherapp.data.model.factory.WeatherFactory
import com.musalasoft.weatherapp.data.net.Result
import com.musalasoft.weatherapp.data.net.response.ErrorResponse
import com.musalasoft.weatherapp.data.net.response.WeatherResponse
import com.musalasoft.weatherapp.data.net.service.ApiService
import com.musalasoft.weatherapp.ui.view.activities.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * View model associated with [MainActivity]
 */
class MainViewModel : ViewModel() {

    private val isSearchInProgressLiveData = MutableLiveData<Boolean>()
    private var isInternetConnected = false

    var isLocationLoaded = false
    var searchQuery = ObservableField("")
    var weather = ObservableField<WeatherDto>()
    var errorType = ObservableField<ErrorType>()
    var errorMessage = ObservableField("")

    /**
     * Refreshes the weather data of the currently loaded city.
     */
    fun refreshData() {
        weather.get()?.let { searchWeatherByCity(it.cityName) }
    }

    /**
     * If there is an active network connection a weather search is executed for the given
     * coordinates. If a there is not an active connection an error is shown on the UI.
     *
     * @param longitude [Double]
     * @param latitude [Double]
     */
    fun searchWeatherByCoordinates(longitude: Double, latitude: Double) {
        if (isInternetConnected) {
            errorType.set(ErrorType.NONE)

            viewModelScope.launch(Dispatchers.Default) {
                isSearchInProgressLiveData.postValue(true)
                val getWeatherByCoordinatesApiCall = withContext(Dispatchers.IO) {
                    ApiService.getWeatherByCoordinates(latitude, longitude)
                }

                searchWeather(getWeatherByCoordinatesApiCall)

                isSearchInProgressLiveData.postValue(false)
            }
        } else {
            setErrorDetails(
                "network error", ErrorType.NETWORK
            )
        }
    }

    /**
     * Executes a weather search for the [searchQuery]
     */
    fun searchWeatherByQuery() {
        searchQuery.get()?.let { searchWeatherByCity(it) }
    }

    /**
     * Setter for [isInternetConnected]
     */
    fun setInternetConnectivityStatus(isConnected: Boolean) {
        isInternetConnected = isConnected
    }

    /**
     * Updates the error details and sets the current [weather] to null.
     *
     * @param message [String] the error message that is going to be shown
     * @param type [ErrorType] the type of error that is going to be shown
     */
    fun setErrorDetails(message: String, type: ErrorType) {
        weather.set(null)
        errorType.set(type)
        errorMessage.set(message)
    }

    /**
     * Handles the response of a weather search API call. If the API call is successful
     * sets the shown [weather] data and the [searchQuery]. If the API call is unsuccessful
     * a search error is going to be shown to the UI. If the API call throws an exception the
     * exception will be logged.
     *
     * @param apiCallResponse [Result]
     */
    private fun searchWeather(apiCallResponse: Result<WeatherResponse, ErrorResponse>) {
        when (apiCallResponse) {
            is Result.Success<WeatherResponse, *> -> {
                Log.d("MAIN_VIEW_MODEL", apiCallResponse.data.toString())

                val weatherDto =
                    WeatherFactory.fromResponseToDto(apiCallResponse.data)
                weather.set(weatherDto)
                searchQuery.set(weatherDto.cityName)
            }

            is Result.Error<*, ErrorResponse> -> {
                Log.d("MAIN_VIEW_MODEL", apiCallResponse.error.toString())
                if (apiCallResponse.error != null) {
                    setErrorDetails(
                        apiCallResponse.error.message.capitalize(
                            Locale.ROOT
                        ), ErrorType.SEARCH
                    )
                }
            }

            is Result.Exception -> {
                Log.d("MAIN_VIEW_MODEL", apiCallResponse.exception)
            }
        }
    }

    /**
     * If there is an active network connection a weather search is executed for the given
     * [query]. If a there is not an active connection an error is shown on the UI.
     *
     * @param query [String]
     */
    private fun searchWeatherByCity(query: String) {
        if (isInternetConnected) {
            errorType.set(ErrorType.NONE)

            viewModelScope.launch(Dispatchers.Default) {
                isSearchInProgressLiveData.postValue(true)
                val getWeatherByCityApiCall = withContext(Dispatchers.IO) {
                    ApiService.getWeatherByCity(query)
                }

                searchWeather(getWeatherByCityApiCall)

                isSearchInProgressLiveData.postValue(false)
            }
        } else {
            setErrorDetails(
                "network error", ErrorType.NETWORK
            )
        }
    }

    fun getIsSearchInProgressLiveData() = isSearchInProgressLiveData
}
