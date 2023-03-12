package com.mathew.openweather.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mathew.openweather.BuildConfig
import com.mathew.openweather.db.CityDao
import com.mathew.openweather.db.CityDatabase
import com.mathew.openweather.model.City
import com.openweather.content.constant.Languages
import com.openweather.content.constant.Units
import com.openweather.content.implementation.OpenWeatherMapHelper
import com.openweather.content.implementation.callback.CurrentWeatherCallback
import com.openweather.content.model.currentweather.CurrentWeather
import kotlinx.coroutines.coroutineScope


class CityRepository(application: Application) {

    private var cityDao: CityDao? = null
    private var openWeatherHelper: OpenWeatherMapHelper? = null
    private val _currentWeatherLiveData = MutableLiveData<CurrentWeather?>()

    init {
        val db: CityDatabase = CityDatabase.getDatabase(application)
        cityDao = db.cityDao()

        openWeatherHelper = OpenWeatherMapHelper(BuildConfig.API_KEY).apply {
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
            object : CurrentWeatherCallback {
                override fun onSuccess(currentWeather: CurrentWeather?) {
                    _currentWeatherLiveData.postValue(currentWeather)
                }

                override fun onFailure(throwable: Throwable?) {
                    _currentWeatherLiveData.postValue(null)
                    Log.v("TAG", "${throwable?.message}")
                }
            })
    }

    fun getCurrentWeatherLiveData() = _currentWeatherLiveData
}
