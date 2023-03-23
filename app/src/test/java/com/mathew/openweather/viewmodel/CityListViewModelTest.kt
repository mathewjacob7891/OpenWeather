package com.mathew.openweather.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mathew.openweather.db.CityDao
import com.mathew.openweather.model.City
import com.mathew.openweather.repository.CityRepository
import com.mathew.openweather.util.RefUtil.setAndReturnPrivateProperty
import com.mathew.openweather.util.getOrAwaitValue
import com.openweather.content.util.WeatherUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class CityListViewModelTest {

    private lateinit var viewModel: CityListViewModel
    private lateinit var repository: CityRepository
    private lateinit var cityDao: CityDao
    private val cityList = ArrayList<City>().apply {
        add(City("Naples"))
        add(City("Rome"))
        add(City("New York"))
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val application = Mockito.mock(Application::class.java)
        val weatherUtil: WeatherUtil = Mockito.mock(WeatherUtil::class.java)
        cityDao = Mockito.mock(CityDao::class.java)
        repository = CityRepository(weatherUtil, cityDao)
        repository.setAndReturnPrivateProperty("cityDao", cityDao)
        viewModel = CityListViewModel(repository, application)
        viewModel.setAndReturnPrivateProperty("repository", repository)
    }

    @Test
    fun getAllCities() {
        Assert.assertNotNull(viewModel.allCities)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun findAllCities() = runTest {
        Mockito.`when`(repository.findAllCities()).thenReturn(cityList)
        viewModel.findAllCities()
        Assert.assertNotNull(viewModel.allCities)
        viewModel.allCities.getOrAwaitValue {
            Assert.assertEquals(cityList, viewModel.allCities.getOrAwaitValue())
        }
    }
}