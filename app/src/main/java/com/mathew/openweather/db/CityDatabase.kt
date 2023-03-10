package com.mathew.openweather.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mathew.openweather.model.City

@Database(entities = [City::class], version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {

        private const val DB_NAME = "cityList.db"

        @Volatile
        private var instance: CityDatabase? = null

        fun getDatabase(application: Application): CityDatabase {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    application, CityDatabase::class.java, DB_NAME
                ).build()
                Companion.instance = instance
                return instance
            }
        }
    }
}