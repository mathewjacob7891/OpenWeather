package com.mathew.openweather.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.mathew.openweather.model.City
import com.mathew.openweather.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCityViewModel(application: Application) : ViewModel() {

    private var repository: CityRepository
    private var _allCities = MutableLiveData<List<City?>?>()
    val allCities: LiveData<List<City?>?> = _allCities
    val cityName = ObservableField<String>()

    init {
        repository = CityRepository(application)
        findAllCities()
    }

    fun findAllCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _allCities = repository.findAllCities()
        }
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