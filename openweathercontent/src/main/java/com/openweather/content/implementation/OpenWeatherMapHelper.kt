package com.openweather.content.implementation

import com.openweather.content.network.OpenWeatherMapClient.client
import com.openweather.content.network.OpenWeatherMapService
import org.json.JSONObject
import org.json.JSONException
import com.openweather.content.implementation.callback.CurrentWeatherCallback
import com.openweather.content.model.currentweather.CurrentWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.util.HashMap

class OpenWeatherMapHelper(apiKey: String?) {

    private val openWeatherMapService: OpenWeatherMapService = client.create(
        OpenWeatherMapService::class.java
    )
    private val options: MutableMap<String?, String?>

    //SETUP METHODS
    fun setUnits(units: String?) {
        options[UNITS] = units
    }

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

    //GET CURRENT WEATHER BY CITY NAME
    fun getCurrentWeatherByCityName(city: String?, callback: CurrentWeatherCallback) {
        options[QUERY] = city
        openWeatherMapService.getCurrentWeatherByCityName(options)
            .enqueue(object : Callback<CurrentWeather?> {
                override fun onResponse(
                    call: Call<CurrentWeather?>,
                    response: Response<CurrentWeather?>
                ) {
                    handleCurrentWeatherResponse(response, callback)
                }

                override fun onFailure(call: Call<CurrentWeather?>, throwable: Throwable) {
                    callback.onFailure(throwable)
                }
            })
    }

    private fun handleCurrentWeatherResponse(
        response: Response<CurrentWeather?>,
        callback: CurrentWeatherCallback
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

    init {
        options = HashMap()
        options[APPID] = apiKey ?: ""
    }
}
