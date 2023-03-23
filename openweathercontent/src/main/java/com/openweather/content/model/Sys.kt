package com.openweather.content.model

import com.google.gson.annotations.SerializedName
import com.openweather.content.util.unixTimestampToTimeString

class Sys {
    @SerializedName("type")
    val type = 0.0

    @SerializedName("id")
    val id: Long? = null

    @SerializedName("message")
    val message: Double? = null

    @SerializedName("country")
    val country: String? = null

    @SerializedName("sunrise")
    val sunrise: Long? = null

    @SerializedName("sunset")
    val sunset: Long? = null

    @SerializedName("pod")
    val pod: Char? = null

    fun getSunriseString(): String? = sunrise?.unixTimestampToTimeString()
    fun getSunsetString(): String? = sunset?.unixTimestampToTimeString()
}
