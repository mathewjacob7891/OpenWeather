package com.mathew.openweather.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mathew.openweather.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCityViewModel(application: Application) : ViewModel() {

    private var repository: CityRepository
    val cityName = ObservableField<String>()

    init {
        repository = CityRepository(application)
    }

    fun saveCity() {
        cityName.get()?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addCity(it)
            }
        }
    }
}

class AddCityViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AddCityViewModel(application) as T
}