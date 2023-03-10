package com.mathew.openweather.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mathew.openweather.repository.CityRepository

class CityWeatherInfoViewModel(application: Application) : ViewModel() {

    private var repository: CityRepository

    init {
        repository = CityRepository(application)
    }

    fun fetchWeatherInfoFromCityName(cityName: String) {
        repository.fetchWeatherInfoFromCityName(cityName)
    }

    fun getCurrentWeatherLiveData() = repository.getCurrentWeatherLiveData()
}

class CityWeatherInfoViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CityWeatherInfoViewModel(application) as T
}