package com.mathew.openweather.db

import androidx.room.*
import com.mathew.openweather.model.City

@Dao
interface CityDao {

    @get:Query("SELECT * FROM city")
    val all: List<City>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City?)

    @Delete
    fun delete(city: City?)

    @Update
    fun update(city: City?)

}