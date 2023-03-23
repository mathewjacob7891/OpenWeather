package com.openweather.content.service

import retrofit2.http.GET
import com.openweather.content.model.Weather
import retrofit2.Call
import retrofit2.http.QueryMap

interface WeatherService {
    //Current Weather Endpoints start
    @GET(CURRENT)
    fun getCurrentWeatherByCityName(@QueryMap options: Map<String?, String?>?): Call<Weather?>

    companion object {
        const val CURRENT = "/data/2.5/weather"
        const val FORECAST = "/data/2.5/forecast"
    }
}
