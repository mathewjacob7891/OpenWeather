package com.mathew.openweather.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mathew.openweather.repository.CityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCityViewModel @Inject constructor(
    private val repository: CityRepository,
    application: Application
) : AndroidViewModel(application) {

    private var _navigationLiveData = MutableLiveData(false)
    val cityName = ObservableField<String>()
    var navigationLiveData: LiveData<Boolean> = _navigationLiveData

    fun saveCity() {
        cityName.get()?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addCity(it)
                _navigationLiveData.postValue(true)
            }
        }
    }
}
