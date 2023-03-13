package com.mathew.openweather.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mathew.openweather.db.CityDao
import com.mathew.openweather.repository.CityRepository
import com.mathew.openweather.util.RefUtil.setAndReturnPrivateProperty
import com.openweather.content.model.common.Main
import com.openweather.content.model.currentweather.CurrentWeather
import org.junit.*
import org.mockito.Mockito

class CityWeatherInfoViewModelTest {

    private lateinit var viewModel: CityWeatherInfoViewModel
    private lateinit var repository: CityRepository
    private lateinit var cityDao: CityDao
    private val cityName = "Naples"
    private val tempCurrentWeather = CurrentWeather(
        main = Main(
            temp = 26.7
        ),
        name = "Naples"
    )

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val application = Mockito.mock(Application::class.java)
        cityDao = Mockito.mock(CityDao::class.java)
        repository = CityRepository(application)
        repository.setAndReturnPrivateProperty("cityDao", cityDao)
        viewModel = CityWeatherInfoViewModel(application)
        viewModel.setAndReturnPrivateProperty("repository", repository)
    }

    @Test
    fun getShowLoader() {
        Assert.assertEquals(true, viewModel.showLoader.get())
    }

    @Test
    fun getCurrentWeather() {
        Assert.assertNull(viewModel.currentWeather.get())
    }

    @Test
    fun fetchWeatherInfoFromCityName() {
        viewModel.fetchWeatherInfoFromCityName(cityName)
        viewModel.currentWeather.set(tempCurrentWeather)
        Assert.assertEquals(tempCurrentWeather, viewModel.currentWeather.get())
    }

    @Test
    fun getCurrentWeatherLiveData() {
        val expectedValue = repository.getCurrentWeatherLiveData().value
        val actualValue = viewModel.getCurrentWeatherLiveData().value
        Assert.assertEquals(expectedValue, actualValue)
    }

    @Test
    fun hideLoader() {
        Assert.assertEquals(true, viewModel.showLoader.get())
        viewModel.hideLoader()
        Assert.assertEquals(false, viewModel.showLoader.get())

    }

    @Test
    fun getStringValueOf() {
        val stringValue1 = viewModel.getStringValueOf(10.4)
        Assert.assertEquals("10.4", stringValue1)
        val stringValue2 = viewModel.getStringValueOf(34)
        Assert.assertEquals("34", stringValue2)
        val stringValue3 = viewModel.getStringValueOf(3F)
        Assert.assertEquals("3.0", stringValue3)
    }
}