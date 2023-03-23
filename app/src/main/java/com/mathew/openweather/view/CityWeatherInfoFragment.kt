package com.mathew.openweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mathew.openweather.R
import com.mathew.openweather.databinding.LayoutCurrentWeatherMainBinding
import com.mathew.openweather.util.Constants.CITY_NAME
import com.mathew.openweather.viewmodel.CityWeatherInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class CityWeatherInfoFragment : Fragment() {

    private var _binding: LayoutCurrentWeatherMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val cityWeatherInfoViewModel: CityWeatherInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = LayoutCurrentWeatherMainBinding.inflate(inflater, container, false)
        binding.viewModel = cityWeatherInfoViewModel
        binding.include2.viewModel = cityWeatherInfoViewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(CITY_NAME)?.let {
            cityWeatherInfoViewModel.fetchWeatherInfoFromCityName(it)
        } ?: run {
            findNavController().popBackStack()
        }
        // TODO: Need to avoid using livedata here and bind directly from ViewModel.
        cityWeatherInfoViewModel.getCurrentWeatherLiveData()
            .observe(viewLifecycleOwner) { currentWeather ->
                currentWeather?.let {
                    cityWeatherInfoViewModel.weather.set(it)
                } ?: run {
                    binding.tvWeatherCondition.text = getString(R.string.place_not_found_error)
                }
                cityWeatherInfoViewModel.hideLoader()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
