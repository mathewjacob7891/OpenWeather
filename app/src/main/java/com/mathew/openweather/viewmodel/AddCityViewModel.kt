package com.mathew.openweather.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mathew.openweather.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCityViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: CityRepository
    val cityName = ObservableField<String>()
    private var _navigationLiveData = MutableLiveData(false)
    var navigationLiveData: LiveData<Boolean> = _navigationLiveData

    init {
        repository = CityRepository(application)
    }

    fun saveCity() {
        cityName.get()?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addCity(it)
                _navigationLiveData.postValue(true)
            }
        }
    }
}
