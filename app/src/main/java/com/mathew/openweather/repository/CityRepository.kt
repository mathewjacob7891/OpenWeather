package com.mathew.openweather.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mathew.openweather.db.CityDao
import com.mathew.openweather.model.City
import com.openweather.content.callback.WeatherCallback
import com.openweather.content.model.Weather
import com.openweather.content.util.WeatherUtil
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val weatherUtil: WeatherUtil,
    private val cityDao: CityDao
) {

    private val _weatherLiveData = MutableLiveData<Weather?>()

    suspend fun findAllCities(): List<City> {
        val allCities = ArrayList<City>()
        coroutineScope {
            cityDao.all?.let {
                allCities.addAll(it)
            }
        }
        return allCities
    }

    fun addCity(cityName: String) {
        cityDao.insert(City(cityName))
    }

    fun fetchWeatherInfoFromCityName(cityName: String) {
        weatherUtil.getCurrentWeatherByCityName(cityName,
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
