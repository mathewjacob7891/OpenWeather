package com.mathew.openweather.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mathew.openweather.db.CityDao
import com.mathew.openweather.repository.CityRepository
import com.mathew.openweather.util.RefUtil.setAndReturnPrivateProperty
import com.mathew.openweather.util.getOrAwaitValue
import com.mathew.openweather.util.observeForTesting
import com.openweather.content.util.WeatherUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Test Case for [AddCityViewModel].
 */
@RunWith(MockitoJUnitRunner::class)
open class AddCityViewModelTest {

    private lateinit var viewModel: AddCityViewModel
    private lateinit var repository: CityRepository
    private lateinit var cityDao: CityDao
    private lateinit var weatherUtil: WeatherUtil

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val application = Mockito.mock(Application::class.java)
        weatherUtil = Mockito.mock(WeatherUtil::class.java)
        cityDao = Mockito.mock(CityDao::class.java)
        repository = CityRepository(weatherUtil, cityDao)
        repository.setAndReturnPrivateProperty("cityDao", cityDao)
        viewModel = AddCityViewModel(repository, application)
        viewModel.setAndReturnPrivateProperty("repository", repository)
    }

    @Test
    fun testSaveCity() = runTest {
        val cityName = "Naples"
        viewModel.cityName.set(cityName)
        viewModel.saveCity()
        viewModel.navigationLiveData.observeForTesting {
            val awaitItem = viewModel.navigationLiveData.getOrAwaitValue()
            assertNotNull(awaitItem)
            delay(100)
            assertEquals(true, viewModel.navigationLiveData.getOrAwaitValue())
        }
    }
}