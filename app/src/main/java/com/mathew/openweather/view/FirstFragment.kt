package com.mathew.openweather.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mathew.openweather.R
import com.mathew.openweather.databinding.FragmentFirstBinding
import com.openweather.content.constant.Languages
import com.openweather.content.constant.Units
import com.openweather.content.implementation.OpenWeatherMapHelper
import com.openweather.content.implementation.callback.CurrentWeatherCallback
import com.openweather.content.model.currentweather.CurrentWeather

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val helper = OpenWeatherMapHelper(resources.getString(R.string.OPEN_WEATHER_MAP_API_KEY))
        helper.setUnits(Units.IMPERIAL)
        helper.setLanguage(Languages.ENGLISH)

        helper.getCurrentWeatherByCityName("Naples", object : CurrentWeatherCallback {
            override fun onSuccess(currentWeather: CurrentWeather?) {
                binding.textviewFirst.text = """
     Coordinates: ${currentWeather?.coord?.lat}, ${currentWeather?.coord?.lon}
     Weather Description: ${currentWeather?.weather?.get(0)?.description}
     Temperature: ${currentWeather?.main?.tempMax}
     Wind Speed: ${currentWeather?.wind?.speed}
     City, Country: ${currentWeather?.name}, ${currentWeather?.sys?.country}
     """.trimIndent()

            }

            override fun onFailure(throwable: Throwable?) {
                Log.v("TAG", throwable!!.message!!)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
