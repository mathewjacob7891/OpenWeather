package com.mathew.openweather.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mathew.openweather.BuildConfig
import com.mathew.openweather.db.CityDao
import com.mathew.openweather.db.CityDatabase
import com.mathew.openweather.model.City
import com.openweather.content.callback.WeatherCallback
import com.openweather.content.constant.Languages
import com.openweather.content.constant.Units
import com.openweather.content.util.WeatherUtil
import com.openweather.content.model.Weather
import kotlinx.coroutines.coroutineScope


class CityRepository(application: Application) {

    private var cityDao: CityDao? = null
    private var openWeatherHelper: WeatherUtil? = null
    private val _weatherLiveData = MutableLiveData<Weather?>()

    init {
        val db: CityDatabase = CityDatabase.getDatabase(application)
        cityDao = db.cityDao()

        openWeatherHelper = WeatherUtil(BuildConfig.API_KEY).apply {
            setUnits(Units.IMPERIAL)
            setLanguage(Languages.ENGLISH)
        }
    }

    suspend fun findAllCities(): List<City> {
        val allCities = ArrayList<City>()
        coroutineScope {
            cityDao?.all?.let {
                allCities.addAll(it)
            }
        }
        return allCities
    }

    fun addCity(cityName: String) {
        cityDao?.insert(City(cityName))
    }

    fun fetchWeatherInfoFromCityName(cityName: String) {
        openWeatherHelper?.getCurrentWeatherByCityName(cityName,
            object : WeatherCallback {
                override fun onSuccess(weather: Weather?) {
                    _weatherLiveData.postValue(weather)
                }

                override fun onFailure(throwable: Throwable?) {
                    _weatherLiveData.postValue(null)
                    Log.v("TAG", "${throwable?.message}")
                }
            })
    }

    fun getCurrentWeatherLiveData() = _weatherLiveData
}
