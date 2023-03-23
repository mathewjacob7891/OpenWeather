package com.mathew.openweather.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mathew.openweather.model.City
import com.mathew.openweather.repository.CityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
    private val repository: CityRepository,
    application: Application
) : AndroidViewModel(application) {

    private var _allCities = MutableLiveData<List<City>?>()
    val allCities: LiveData<List<City>?> = _allCities

    fun findAllCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _allCities.postValue(repository.findAllCities())
        }
    }
}
