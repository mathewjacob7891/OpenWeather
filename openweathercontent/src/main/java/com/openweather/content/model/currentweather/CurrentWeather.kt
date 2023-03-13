package com.openweather.content.model.currentweather

import com.google.gson.annotations.SerializedName
import com.openweather.content.model.common.Clouds
import com.openweather.content.model.common.Coord
import com.openweather.content.model.common.Main
import com.openweather.content.model.common.Rain
import com.openweather.content.model.common.Snow
import com.openweather.content.model.common.Sys
import com.openweather.content.model.common.Weather
import com.openweather.content.model.common.Wind

class CurrentWeather(
    @SerializedName("coord")
    val coord: Coord? = null,

    @SerializedName("weather")
    val weather: List<Weather>? = null,

    @SerializedName("base")
    val base: String? = null,

    @SerializedName("main")
    val main: Main? = null,

    @SerializedName("visibility")
    val visibility: Long? = null,

    @SerializedName("wind")
    val wind: Wind? = null,

    @SerializedName("clouds")
    val clouds: Clouds? = null,

    @SerializedName("rain")
    val rain: Rain? = null,

    @SerializedName("snow")
    val snow: Snow? = null,

    @SerializedName("dt")
    val dt: Long? = null,

    @SerializedName("sys")
    val sys: Sys? = null,

    @SerializedName("timezone")
    val timezone: Long? = null,

    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("cod")
    val cod: Int? = null
)
