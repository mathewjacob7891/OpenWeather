package com.mathew.openweather.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mathew.openweather.model.City
import com.mathew.openweather.db.CityDao
import com.mathew.openweather.db.CityDatabase


class CityRepository(application: Application) {

    private var _allCities = MutableLiveData<List<City?>?>()
//    val allCities: LiveData<List<City?>?> = _allCities
    private var cityDao: CityDao? = null

    init {
        val db: CityDatabase = CityDatabase.getDatabase(application)
        cityDao = db.cityDao()
    }

    fun findAllCities(): MutableLiveData<List<City?>?> {
        cityDao?.all?.let {
            _allCities.postValue(it)
        }
        return _allCities
    }

    fun addCity(cityName: String) {
        cityDao?.insert(City(cityName))
    }
}
