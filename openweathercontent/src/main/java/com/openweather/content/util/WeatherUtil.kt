package com.openweather.content.util

import com.openweather.content.callback.WeatherCallback
import com.openweather.content.service.WeatherClient.client
import com.openweather.content.service.WeatherService
import org.json.JSONObject
import org.json.JSONException
import com.openweather.content.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.util.HashMap

/**
 * Helper Class for Open Weather API. This class contains function for fetching data from Open Weather API.
 */
class WeatherUtil(apiKey: String?) {

    private val weatherService: WeatherService = client.create(
        WeatherService::class.java
    )
    private val options: MutableMap<String?, String?>

    init {
        options = HashMap()
        options[APPID] = apiKey ?: ""
    }

    /**
     * Function to set the unit in which Current Weather info is available.
     *
     * @param units pass imperial / metric according to the required result.
     */
    fun setUnits(units: String?) {
        options[UNITS] = units
    }

    /**
     * Function to set the Language in which OpenWeather API needs to respond.
     *
     * @param lang string variable for the chosen language.
     */
    fun setLanguage(lang: String?) {
        options[LANGUAGE] = lang
    }

    private fun noAppIdErrMessage(): Throwable {
        return Throwable("UnAuthorized. Please set a valid OpenWeatherMap API KEY.")
    }

    private fun notFoundErrMsg(str: String?): Throwable {
        var throwable: Throwable? = null
        try {
            val obj = JSONObject(str ?: "")
            throwable = Throwable(obj.getString("message"))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        if (throwable == null) {
            throwable = Throwable("An unexpected error occurred.")
        }
        return throwable
    }

    /**
     * Function to fetch Current Weather information using City Name.
     *
     * @param city name of the city/country/place to which weather information needs to be fetched.
     * @param callback interface to handle the response of Current Weather information.
     */
    fun getCurrentWeatherByCityName(city: String?, callback: WeatherCallback) {
        options[QUERY] = city
        weatherService.getCurrentWeatherByCityName(options)
            .enqueue(object : Callback<Weather?> {
                override fun onResponse(
                    call: Call<Weather?>,
                    response: Response<Weather?>
                ) {
                    handleCurrentWeatherResponse(response, callback)
                }

                override fun onFailure(call: Call<Weather?>, throwable: Throwable) {
                    callback.onFailure(throwable)
                }
            })
    }

    private fun handleCurrentWeatherResponse(
        response: Response<Weather?>,
        callback: WeatherCallback
    ) {
        if (response.code() == HttpURLConnection.HTTP_OK) {
            callback.onSuccess(response.body())
        } else if (response.code() == HttpURLConnection.HTTP_FORBIDDEN || response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            callback.onFailure(noAppIdErrMessage())
        } else {
            try {
                callback.onFailure(notFoundErrMsg(response.errorBody()?.string()))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val APPID = "appId"
        private const val UNITS = "units"
        private const val LANGUAGE = "lang"
        private const val QUERY = "q"
    }
}
