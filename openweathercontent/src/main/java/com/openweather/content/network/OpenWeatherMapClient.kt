package com.openweather.content.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherMapClient {
    private const val BASE_URL = "https://api.openweathermap.org"
    private lateinit var retrofit: Retrofit

    val client: Retrofit
        get() {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }
}
