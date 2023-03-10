package com.mathew.openweather.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.mathew.openweather.model.City
import com.mathew.openweather.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityListViewModel(application: Application) : ViewModel() {

    private var repository: CityRepository
    private var _allCities = MutableLiveData<List<City>?>()
    val allCities: LiveData<List<City>?> = _allCities

    init {
        repository = CityRepository(application)
    }

    fun findAllCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _allCities.postValue(repository.findAllCities())
        }
    }
}

class CityListViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CityListViewModel(application) as T
}