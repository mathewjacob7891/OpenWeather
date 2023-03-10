package com.mathew.openweather.repository

import android.app.Application
import com.mathew.openweather.db.CityDao
import com.mathew.openweather.db.CityDatabase
import com.mathew.openweather.model.City
import kotlinx.coroutines.coroutineScope


class CityRepository(application: Application) {

    private var cityDao: CityDao? = null

    init {
        val db: CityDatabase = CityDatabase.getDatabase(application)
        cityDao = db.cityDao()
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
}
