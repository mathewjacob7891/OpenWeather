package com.mathew.openweather.di

import android.app.Application
import com.mathew.openweather.BuildConfig
import com.mathew.openweather.db.CityDao
import com.mathew.openweather.db.CityDatabase
import com.openweather.content.constant.Languages
import com.openweather.content.constant.Units
import com.openweather.content.util.WeatherUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideWeatherUtil(): WeatherUtil =
        WeatherUtil(BuildConfig.API_KEY).apply {
            setUnits(Units.IMPERIAL)
            setLanguage(Languages.ENGLISH)
        }

    @Provides
    @Singleton
    fun provideCityDao(application: Application): CityDao {
        val db: CityDatabase = CityDatabase.getDatabase(application)
        return db.cityDao()
    }
}