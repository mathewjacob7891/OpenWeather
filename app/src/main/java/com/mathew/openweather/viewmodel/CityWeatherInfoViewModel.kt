package com.mathew.openweather.viewmodel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.mathew.openweather.repository.CityRepository
import com.openweather.content.model.Weather

class CityWeatherInfoViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: CityRepository
    val showLoader = ObservableBoolean(true) // 0 -> VISIBLE, 8 -> GONE
    var weather = ObservableField<Weather>()

    init {
        repository = CityRepository(application)
    }

    fun fetchWeatherInfoFromCityName(cityName: String) {
        showLoader.set(true)
        repository.fetchWeatherInfoFromCityName(cityName)
    }

    fun getCurrentWeatherLiveData() = repository.getCurrentWeatherLiveData()

    fun hideLoader() {
        showLoader.set(false)
    }

    fun getStringValueOf(any: Any?) = any.toString()
}
