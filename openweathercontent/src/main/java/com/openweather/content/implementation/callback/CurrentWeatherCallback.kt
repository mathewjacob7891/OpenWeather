package com.openweather.content.implementation.callback

import com.openweather.content.model.currentweather.CurrentWeather

interface CurrentWeatherCallback {
    fun onSuccess(currentWeather: CurrentWeather?)
    fun onFailure(throwable: Throwable?)
}
