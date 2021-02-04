package com.musalasoft.weatherapp.data.net

import com.musalasoft.weatherapp.data.net.response.ErrorResponse

sealed class Result<out T, out U> {
    data class Success<T, U>(val data: T) : Result<T, Nothing>()
    data class Error<T, U>(val error: ErrorResponse?) : Result<Nothing, U>()
    data class Exception(val exception: String) : Result<Nothing, Nothing>()
}