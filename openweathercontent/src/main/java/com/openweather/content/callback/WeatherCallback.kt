package com.openweather.content.callback

import com.openweather.content.model.Weather

interface WeatherCallback {
    fun onSuccess(weather: Weather?)
    fun onFailure(throwable: Throwable?)
}
