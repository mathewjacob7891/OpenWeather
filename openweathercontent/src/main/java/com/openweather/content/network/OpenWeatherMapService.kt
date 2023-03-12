package com.openweather.content.network

import retrofit2.http.GET
import com.openweather.content.model.currentweather.CurrentWeather
import retrofit2.Call
import retrofit2.http.QueryMap

interface OpenWeatherMapService {
    //Current Weather Endpoints start
    @GET(CURRENT)
    fun getCurrentWeatherByCityName(@QueryMap options: Map<String?, String?>?): Call<CurrentWeather?>

    companion object {
        const val CURRENT = "/data/2.5/weather"
        const val FORECAST = "/data/2.5/forecast"
    }
}
